package com.example.wemi.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.wemi.R
import com.example.wemi.retrofit.HospitalData

class HospitalListAdapter :
    ListAdapter<HospitalData, HospitalListAdapter.ItemViewHolder>(differ) {
    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hospitalModel: HospitalData) {
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val priceTextView = view.findViewById<TextView>(R.id.priceTextView)
            val thumbnailImageView = view.findViewById<ImageView>(R.id.thumbnailImageView)

            titleTextView.text = hospitalModel.title
            priceTextView.text = hospitalModel.price

            Glide.with(thumbnailImageView.context)
                .load(hospitalModel.imgUrl)
                .transform(CenterCrop(), RoundedCorners(dp2px(thumbnailImageView.context, 12)))
                // centerCrop : 실제 이미지가 이미지뷰의 사이즈보다 클 때, 이미지뷰의 크기에 맞춰 이미지 중간부분을 잘라서 스케일링한다.
                // RoundedCorners : 픽셀 단위로 둥글게. -> 변환함수 구현.
                .into(thumbnailImageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            inflater.inflate(
                R.layout.item_hospital,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun dp2px(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
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