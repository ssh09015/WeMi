package com.example.wemi.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wemi.databinding.ActivityReviewDetailBinding


class ReviewAdapter : ListAdapter<ReviewModel, ReviewAdapter.ViewHolder>(diffUtill) {

    inner class ViewHolder(private val binding: ActivityReviewDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewModel: ReviewModel) {
            binding.id.text = reviewModel.id
            binding.comment.text = reviewModel.review
            binding.ratingbar.setIsIndicator(false)
            binding.ratingbar.rating = reviewModel.score.toFloat()

        }
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
}