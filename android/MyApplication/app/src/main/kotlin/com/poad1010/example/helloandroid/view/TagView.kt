package com.poad1010.example.helloandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.poad1010.example.helloandroid.R

/**
 * TODO: document your custom view class.
 */
class TagView : View {
    private var tag: String? = null // TODO: use a default from R.string...
    private var color = Color.RED // TODO: use a default from R.color...
    private var mDimension = 0f // TODO: use a default from R.dimen...
    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param drawable The example drawable attribute value to use.
     */
    var drawable: Drawable? = null

    private var mTextPaint: TextPaint? = null
    private var mTextWidth: Float = 0.toFloat()
    private var mTextHeight: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.TagView, defStyle, 0)

        tag = a.getString(
                R.styleable.TagView_exampleString)
        color = a.getColor(
                R.styleable.TagView_exampleColor,
                color)
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mDimension = a.getDimension(
                R.styleable.TagView_exampleDimension,
                mDimension)

        if (a.hasValue(R.styleable.TagView_exampleDrawable)) {
            drawable = a.getDrawable(
                    R.styleable.TagView_exampleDrawable)
            drawable!!.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        mTextPaint = TextPaint()
        mTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
        mTextPaint!!.textAlign = Paint.Align.LEFT

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        mTextPaint!!.textSize = mDimension
        mTextPaint!!.color = color
        tag?.let {
            mTextWidth = mTextPaint!!.measureText(tag)
        }

        val fontMetrics = mTextPaint!!.fontMetrics
        mTextHeight = fontMetrics.bottom
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        // Draw the text.
        canvas.drawText(tag,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint)

        // Draw the example drawable on top of the text.
        if (drawable != null) {
            drawable!!.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight)
            drawable!!.draw(canvas)
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    override fun getTag(): String? {
        return tag
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param tag The example string attribute value to use.
     */
    fun setTag(tag: String) {
        this.tag = tag
        invalidateTextPaintAndMeasurements()
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    fun getColor(): Int {
        return color
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    fun setColor(exampleColor: Int) {
        color = exampleColor
        invalidateTextPaintAndMeasurements()
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param dimension The example dimension attribute value to use.
     */
    var dimension: Float
        get() = mDimension
        set(dimension) {
            mDimension = dimension
            invalidateTextPaintAndMeasurements()
        }
}
