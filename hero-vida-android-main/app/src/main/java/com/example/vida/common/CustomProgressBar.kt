package com.example.vida.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.appcompat.widget.AppCompatSeekBar
import com.example.vida.common.ProgressItem
import kotlin.jvm.Synchronized
import android.graphics.RectF
import android.util.AttributeSet
import com.example.vida.utils.extension.drawLeftRoundRect
import com.example.vida.utils.extension.drawRightRoundRect
import java.util.ArrayList

class CustomProgressBar : AppCompatSeekBar {
    private var mProgressItemsList: ArrayList<ProgressItem>? = null

    constructor(context: Context?) : super(context!!) {
        mProgressItemsList = ArrayList()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
    }

    fun initData(progressItemsList: ArrayList<ProgressItem>?) {
        mProgressItemsList = progressItemsList
    }

    @Synchronized
    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        if (mProgressItemsList!!.size > 0) {
            val progressBarWidth = width
            val progressBarHeight = height
            val thumboffset = thumbOffset
            var lastProgressX = 0
            var progressItemWidth: Int
            var progressItemRight: Int
            for (i in mProgressItemsList!!.indices) {
                val progressItem = mProgressItemsList!![i]
                val progressPaint = Paint()
                progressPaint.color = resources.getColor(
                    progressItem.color
                )
                progressItemWidth = (progressItem.progressItemPercentage
                        * progressBarWidth / 100).toInt()
                progressItemRight = lastProgressX + progressItemWidth

                // for last item give right to progress item to the width
                if (i == mProgressItemsList!!.size - 1
                    && progressItemRight != progressBarWidth
                ) {
                    progressItemRight = progressBarWidth
                }
                val progressRect = RectF()
                progressRect[lastProgressX.toFloat(), (thumboffset / 2).toFloat(), progressItemRight.toFloat()] =
                    (progressBarHeight - thumboffset / 2).toFloat()
                if (i == 0) {
                    canvas.drawLeftRoundRect(progressRect, progressPaint, 20f)
                } else if (i == 1) {
                    canvas.drawRect(progressRect, progressPaint)
                } else {
                    canvas.drawRightRoundRect(progressRect, progressPaint, 40f)
                }
                lastProgressX = progressItemRight
            }
            super.onDraw(canvas)
        }
    }
}