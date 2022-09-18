package com.example.wemi.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.wemi.R

class CommunityQuestionAdapter (private var items : MutableList<CommunityModel>) : BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): CommunityModel = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_community_content, parent, false)
        }

        val sort = view?.findViewById<TextView>(R.id.comSort)
        val nickname = view?.findViewById<TextView>(R.id.comNickname)
        val title = view?.findViewById<TextView>(R.id.comTitle)
        val content = view?.findViewById<TextView>(R.id.comContent)

        sort!!.text = items[position].sort
        nickname!!.text = items[position].nickname
        title!!.text = items[position].title
        content!!.text = items[position].content

        return view!!
    }
}