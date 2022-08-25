package com.example.wemi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wemi.R
import com.example.wemi.retrofit.HospitalData

class HospitalViewPagerAdapter (val itemClicked: (HospitalData) -> Unit) :
    ListAdapter<HospitalData, HospitalViewPagerAdapter.ItemViewHolder>(differ) {
    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hospitalData: HospitalData) {
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val priceTextView = view.findViewById<TextView>(R.id.priceTextView)
            val thumbnailImageView = view.findViewById<ImageView>(R.id.thumbnailImageView)

            titleTextView.text = hospitalData.title
            priceTextView.text = hospitalData.price

            view.setOnClickListener {
                itemClicked(hospitalData)
            }

            Glide.with(thumbnailImageView.context)
                .load(hospitalData.imgUrl)
                .into(thumbnailImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            inflater.inflate(
                R.layout.item_hospital_detail_for_viewpager,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<HospitalData>() {
            override fun areItemsTheSame(oldItem: HospitalData, newItem: HospitalData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HospitalData, newItem: HospitalData): Boolean {
                return oldItem == newItem
            }

        }
    }

}