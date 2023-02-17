package com.padcmyanmar.smtz.wechatredesign.views.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import com.padcmyanmar.smtz.wechatredesign.R

class RoundCornerImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private var cornerRadius = 0f    // modify from layout
    private var path = Path()

    init {
        context.withStyledAttributes(attrs, R.styleable.RoundCornerImageView) {
            cornerRadius = getDimension(R.styleable.RoundCornerImageView_cornerRadius, 0f)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val rectangle = RectF(0f, 0f, width.toFloat(), height.toFloat())

        path.addRoundRect(rectangle, cornerRadius, cornerRadius, Path.Direction.CCW)
        canvas?.clipPath(path)

        super.onDraw(canvas)
    }

}