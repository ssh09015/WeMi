package com.example.wemi.review

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wemi.R
import com.example.wemi.databinding.ActivityReviewDetailBinding

class ReviewAdapter() :
    ListAdapter<ReviewModel, ReviewAdapter.ViewHolder>(diffUtill), Filterable {
    var TAG="ReviewAdapter"

    //var filteredPersons=ArrayList<ReviewModel>()
    val persons =mutableListOf<ReviewModel>()
    var filteredPersons=mutableListOf<ReviewModel>()
    var itemFilter=ItemFilter()

    inner class ViewHolder(private val binding: ActivityReviewDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var id:TextView
        var comment:TextView

        init{
            id=itemView.findViewById(R.id.id)
            comment=itemView.findViewById(R.id.comment)
        }

        fun bind(reviewModel: ReviewModel) {
            binding.id.text = reviewModel.id
            binding.comment.text = reviewModel.review
            binding.ratingbar.setIsIndicator(false)
            binding.ratingbar.rating = reviewModel.score.toFloat()
        }
    }
    init {
        filteredPersons.addAll(persons)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ActivityReviewDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        val person: ReviewModel =currentList[position]
        holder.id.text=person.id
        holder.comment.text=person.review
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    companion object {
        val diffUtill = object : DiffUtil.ItemCallback<ReviewModel>() {
            override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem.review == newItem.review
            }

            override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    //-- filter
    inner class ItemFilter : Filter() {
        // SeachView에서 입력받은 문자열 charSequence에 따른 처리
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()
            Log.d(TAG, "charSequence : $charSequence")

            //검색이 필요없을 경우를 위해 원본 배열을 복제
            var filteredList=mutableListOf<ReviewModel>()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = persons
                results.count = persons.size

                return results
            }  else {
                for (person in persons) {
                    if (person.id.contains(filterString) || person.review.contains(filterString)) {
                        /*ReviewModelList.add(reviewModel)
                        reviewAdapter.submitList(ReviewModelList)*/
                        filteredList.add(person)

                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredPersons.clear()
            filteredPersons.addAll(filterResults.values as ArrayList<ReviewModel>)
            notifyDataSetChanged()
        }
    }
}