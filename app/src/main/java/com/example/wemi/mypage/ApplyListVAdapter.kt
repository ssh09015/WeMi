package com.example.wemi.mypage

import com.example.wemi.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wemi.support.SupportModel


class ApplyListVAdapter(val items: ArrayList<SupportModel>, val keyList : ArrayList<String>, val applyIdList : MutableList<String>): RecyclerView.Adapter<ApplyListVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: SupportModel) {

//            itemView.setOnClickListener {
//                val intent = Intent(context, ContentShowActivity::class.java)
//                intent.putExtra("url", item.webUrl)
//                itemView.context.startActivity(intent)
//            }

            val organization = itemView.findViewById<TextView>(R.id.organizationArea)
            val title = itemView.findViewById<TextView>(R.id.titleArea)

            organization.text = item.organization
            title.text = item.title
        }
    }
}