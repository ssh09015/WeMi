package com.example.wemi.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wemi.databinding.ActivityWriteReviewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_write_review.*

class WriteReviewActivity : AppCompatActivity() {

    lateinit var articleDB: DatabaseReference
    /*lateinit var reviewAdapter: ReviewAdapter*/
    private val reviewAdapter:ReviewAdapter by lazy{
        ReviewAdapter()
    }




    private val ReviewModelList = mutableListOf<ReviewModel>()
    var user=Firebase.auth.currentUser
    var ratingscore=0.0
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
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //val title=intent.getStringExtra("title")

        binding.ratingbar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.ratingbarText.text = "${rating}점"
            ratingscore = rating.toDouble()

        }

        //데이터 중복 방지
        ReviewModelList.clear()
        //추가할 데이터 위치 설정
        articleDB = Firebase.database.reference.child(ReviewDBkey.DB_REVIEWS)

        binding.reviewButton.setOnClickListener {
            articleDB.addChildEventListener(listener)
            val dotString : String = user?.email.toString()
            val splitArray = dotString.split(".")
            val emailString : String = splitArray[0].toString()
            val splitNameArray = emailString.split("@")
            val comment = binding.comment.text.toString()
            val id = splitNameArray[0]
            val hospitalName=intent.getStringExtra("title")
            val reviewModel = ReviewModel("$hospitalName",id,comment,ratingscore)

            articleDB.push().setValue(reviewModel)
            finish()
        }
    }
}