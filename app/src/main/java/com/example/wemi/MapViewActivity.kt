package com.example.wemi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.wemi.adapter.HospitalViewPagerAdapter
import com.example.wemi.databinding.ActivityMapViewBinding
import com.example.wemi.retrofit.HospitalData
import com.example.wemi.retrofit.HospitalDto
import com.example.wemi.retrofit.RetrofitService
import com.example.wemi.review.ReviewMain
import com.example.wemi.review.WriteReviewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
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
import retrofit2.http.GET




class MapViewActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var naverMap : NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapView: MapView by lazy { findViewById(R.id.mapView) }
    private val viewPager:ViewPager2 by lazy { findViewById(R.id.hospitalViewPager) }
    private val viewPagerAdapter=HospitalViewPagerAdapter(itemClicked = {
        onHospitalDataClicked(hospitalData = it)
    })
    private val currentLocationButton: LocationButtonView by lazy { findViewById(R.id.currentLocationButton) }


    // 뷰페이저 클릭 이벤트
    private fun onHospitalDataClicked(hospitalData: HospitalData) {
        val intent = Intent(this, ReviewMain::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMapViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mapView.onCreate(savedInstanceState)
        // 맵가져오기
        mapView.getMapAsync(this)
        initHospitalViewPager()

        val nav_bar = findViewById<BottomNavigationView>(R.id.nav_bar)

        /*// BottomNavigation 기능 구현
        nav_bar.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.community -> { // community 이름 버튼 누르면 MainActivity 이동
                        myStartActivity(MainActivity::class.java)
                        // Respond to navigation item 2 click
                        true // 해당 버튼 활성화
                    }
                    R.id.support -> { // support 이름 버튼 누르면 SupportActivity로 이동
                        myStartActivity(SupportActivity::class.java)
                        // Respond to navigation item 3 click
                        true
                    }
                    R.id.myPage -> { // myPage 이름 버튼 누르면 MypageActivity로 이동
                        myStartActivity(MypageActivity::class.java)
                        // Respond to navigation item 4 click
                        true
                    }
                }
                true // 지금 선택된 Id 버튼 활성화
            }
            selectedItemId=R.id.location // 지금 선택할 Id 버튼을 community라고 지정
        }*/
    }

    // 애니메이션 사용하지 않고 Intent로 화면전환하는 함수
    private fun myStartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
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
