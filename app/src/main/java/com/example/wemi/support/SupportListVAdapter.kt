package com.example.wemi.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.wemi.R
import com.example.wemi.support.SupportModel

class SupportListVAdapter(val supportList : MutableList<SupportModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return supportList.size
    }

    override fun getItem(position: Int): Any {
        return supportList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, converview: View?, parent: ViewGroup?): View {

        var view = converview

        if (view == null){

            view = LayoutInflater.from(parent?.context).inflate(R.layout.support_list_item, parent, false)

        }

        return view!!
    }
}