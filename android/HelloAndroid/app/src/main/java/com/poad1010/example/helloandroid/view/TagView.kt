package com.poad1010.example.helloandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.poad1010.example.helloandroid.R
import com.poad1010.example.helloandroid.model.Tag

/**
 * TODO: document your custom view class.
 */
class TagView : FrameLayout {

    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyle: Int) : super(context!!, attrs, defStyle)

    var titleTextView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.tag_view, this)
        titleTextView = findViewById(R.id.title_text_view)
    }

    fun setQiitaTag(tag: Tag) {
        titleTextView?.text = tag.id
    }
}
