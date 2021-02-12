package com.poad1010.example.helloandroid.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.poad1010.example.helloandroid.model.Tag
import com.poad1010.example.helloandroid.view.TagView

class TagListAdapter(private val context: Context) : BaseAdapter() {

    var tags : List<Tag> = emptyList()

    override fun getCount(): Int = tags.size

    override fun getItem(p0: Int): Any = tags[p0]

    override fun getItemId(p0: Int): Long = 0


    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup?): View {
        return ((convertView as? TagView) ?: TagView(context)).apply {
            setQiitaTag(tags[position])
        }
    }
}