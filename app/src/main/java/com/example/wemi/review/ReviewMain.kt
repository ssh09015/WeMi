package com.example.wemi.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wemi.R
import com.example.wemi.databinding.ActivityReviewMainBinding
import com.example.wemi.databinding.ActivityWriteReviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_review_main.*

class ReviewMain : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {

    lateinit var articleDB: DatabaseReference
    lateinit var reviewAdapter: ReviewAdapter

    lateinit var search_view: SearchView
    lateinit var reviewRecyclerView:RecyclerView



    var firestore:FirebaseFirestore?=null

    val binding by lazy { ActivityWriteReviewBinding.inflate(layoutInflater) }
    private  lateinit var auth: FirebaseAuth

    private val ReviewModelList = mutableListOf<ReviewModel>()


    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val reviewModel = snapshot.getValue(ReviewModel::class.java)
            reviewModel ?: return

            ReviewModelList.add(reviewModel)
            reviewAdapter.submitList(ReviewModelList)

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }
        //
        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityReviewMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        reviewRecyclerView=findViewById(R.id.reviewRecyclerView)
        search_view=findViewById(R.id.search_view)

        search_view.setOnQueryTextListener(searchViewTextListener)


        firestore=FirebaseFirestore.getInstance()
        ReviewModelList.clear()
        reviewAdapter = ReviewAdapter()
        articleDB = Firebase.database.reference.child(ReviewDBkey.DB_REVIEWS)
        binding.reviewRecyclerView.adapter = reviewAdapter
        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(this)
        articleDB.addChildEventListener(listener)


        binding.review.setOnClickListener {
            val intent=Intent(this, WriteReviewActivity::class.java)
            startActivity(intent)
        }

    }
    var searchViewTextListener:SearchView.OnQueryTextListener=
        object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s:String):Boolean{
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                reviewAdapter.getFilter().filter(s)
                return false
            }
        }


    override fun onResume() {
        super.onResume()
        reviewAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        articleDB.removeEventListener(listener)
    }
    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {

    }
}
