package com.example.wemi.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wemi.R
import com.example.wemi.databinding.ActivityIntroBinding.inflate
import com.example.wemi.databinding.ActivityReviewMainBinding
import com.example.wemi.databinding.ActivityWriteReviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_review_main.*

class ReviewMain : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {
    private lateinit var articleDB: DatabaseReference
    private lateinit var reviewAdapter: ReviewAdapter

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

    /*private val ReviewModelList = mutableListOf<ReviewModel>()
    val user = Firebase.auth.currentUser
    var ratingscore = 0.0
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val reviewModel = snapshot.getValue(ReviewModel::class.java)
            reviewModel ?: return
            ReviewModelList.add(reviewModel)
            reviewAdapter.submitList(ReviewModelList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityReviewMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val title=intent.getStringExtra("title")


        firestore=FirebaseFirestore.getInstance()
        ReviewModelList.clear()
        reviewAdapter = ReviewAdapter()
        articleDB = Firebase.database.reference.child(ReviewDBkey.DB_REVIEWS)
        binding.reviewRecyclerView.adapter = reviewAdapter
        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(this)
        articleDB.addChildEventListener(listener)



        binding.review.setOnClickListener {
            val intent=Intent(this, WriteReviewActivity::class.java)
            intent.putExtra("title",title)
            startActivity(Intent(this,WriteReviewActivity::class.java))
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
