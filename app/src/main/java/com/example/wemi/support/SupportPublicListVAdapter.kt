package com.example.wemi.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.wemi.R

class SupportPublicListVAdapter(val supportPublicList : MutableList<SupportModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return supportPublicList.size
    }

    override fun getItem(position: Int): Any {
        return supportPublicList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, converview: View?, parent: ViewGroup?): View {

        var view = converview

        if (view == null){

            view = LayoutInflater.from(parent?.context).inflate(R.layout.support_list_item, parent, false)

        }

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val organization = view?.findViewById<TextView>(R.id.organizationArea)
        val period = view?.findViewById<TextView>(R.id.periodArea)
        val target = view?.findViewById<TextView>(R.id.targetArea)

        title!!.text = supportPublicList[position].title
        organization!!.text = supportPublicList[position].organization
        period!!.text = supportPublicList[position].period
        target!!.text = supportPublicList[position].target

        return view!!
    }
}