package com.example.wemi

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.wemi.adapter.HospitalListAdapter
import com.example.wemi.adapter.HospitalViewPagerAdapter
import com.example.wemi.retrofit.HospitalData
import com.example.wemi.retrofit.HospitalDto
import com.example.wemi.retrofit.RetrofitService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapViewActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var naverMap : NaverMap
    private lateinit var locationSource: FusedLocationSource

    private val mapView: MapView by lazy { findViewById(R.id.mapView) }

    private val viewPager:ViewPager2 by lazy { findViewById(R.id.hospitalViewPager) }
    private val viewPagerAdapter=HospitalViewPagerAdapter(itemClicked = {
        onHospitalDataClicked(hospitalData = it)
    })

    private val recyclerView:RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private val recyclerViewAdapter=HospitalListAdapter()

    private val currentLocationButton: LocationButtonView by lazy { findViewById(R.id.currentLocationButton) }

    private val bottomSheetTitleTextView: TextView by lazy { findViewById(R.id.bottomSheetTitleTextView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)

        mapView.onCreate(savedInstanceState)

        // 맵가져오기
        mapView.getMapAsync(this)

        initHospitalViewPager()

        initHospitalRecyclerView()
    }

    // 맵 가져오기(from: getMapAsync)
    override fun onMapReady(map: NaverMap) {
        naverMap = map
        // 확대 축소
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        // 지도 위치 이동
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.497801, 127.027591))
        naverMap.moveCamera(cameraUpdate)

        // 현위치 버튼 기능
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false // 뷰 페이져에 가려져 이후 레이아웃에 정의 하였음.

        currentLocationButton.map = naverMap // 이후 정의한 현위치 버튼에 네이버맵 연결

        // -> onRequestPermissionsResult // 위치 권한 요청
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

        // 지도 다 로드 이후에 가져오기
        getHospitalDataAPI()
    }

    private fun getHospitalDataAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()

        retrofit.create(RetrofitService::class.java).also {
            it.getHospitalData()
                .enqueue(object : Callback<HospitalDto> {
                    override fun onResponse(call: Call<HospitalDto>, response: Response<HospitalDto>) {
                        if (response.isSuccessful.not()) {
                            // fail
                            Log.d("Retrofit", "실패1")

                            return
                        }

                        // 성공한 경우 아래 처리
                        response.body()?.let { dto ->
                            updateMarker(dto.items)
                            viewPagerAdapter.submitList(dto.items)
                            recyclerViewAdapter.submitList(dto.items)
                            bottomSheetTitleTextView.text = "${dto.items.size}개의 숙소"
                        }
                    }

                    override fun onFailure(call: Call<HospitalDto>, t: Throwable) {
                        // 실패 처리 구현;
                        Log.d("Retrofit", "실패2")
                        Log.d("Retrofit", t.stackTraceToString())
                    }

                })
        }
    }

    private fun initHospitalViewPager() {
        viewPager.adapter = viewPagerAdapter

        // page 변경시 처리
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedHospitalData = viewPagerAdapter.currentList[position]
                val cameraUpdate =
                    CameraUpdate.scrollTo(LatLng(selectedHospitalData.lat, selectedHospitalData.lng))
                        .animate(CameraAnimation.Easing)
                naverMap.moveCamera(cameraUpdate)
            }
        })
    }

    private fun initHospitalRecyclerView() {
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun updateMarker(hospitals: List<HospitalData>) {
        hospitals.forEach { hospital ->

            val marker = Marker()
            marker.position = LatLng(hospital.lat, hospital.lng)
            marker.onClickListener = this // 마커 클릭 시 뷰 페이져 연동 되도록 구현
            marker.map = naverMap
            marker.tag = hospital.id
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE)
            return

        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                // 권한 설정 거부시 위치 추적을 사용하지 않음
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }

    // 지도 marker 클릭 시
    override fun onClick(overlay: Overlay): Boolean {
        // overlay : 마커 (맵 위에 있는 것들이 눌리면 반응하는 것)
        overlay.tag
        Log.d("onClick",overlay.tag.toString())

        val selectedModel = viewPagerAdapter.currentList.firstOrNull {
            it.id == overlay.tag
        }
        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            viewPager.currentItem = position
        }
        return true
    }

    private fun onHospitalDataClicked(hospitalData: HospitalData) {
        // 공유 기능; 인텐트에있는 츄져사용할것임
        val intent = Intent()
            .apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "[지금 이 가격에 예약하세요!!] ${hospitalData.title} ${hospitalData.price} 사진 보기(${hospitalData.imgUrl}",
                )
                type = "text/plain"
            }
        startActivity(Intent.createChooser(intent, null))
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
