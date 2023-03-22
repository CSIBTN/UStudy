package com.example.ustudy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.ColorUtils

class Note(
    context: Context,
    attributeSet: AttributeSet,
    attributeStyle: Int,
) : View(context, attributeSet, attributeStyle) {

    private val rectanglePath: Path = Path()
    private val cutCornerSize = 30F
    private val cornerRadius = 20F
    private val customPaint = Paint()
    private val customPaint2 = Paint()

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.Note,
            0, 0
        ).apply {
            try {
                customPaint.color = getColor(R.styleable.Note_noteColor, Color.RED)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rectanglePath.apply {
            lineTo(width - dpToPx(cutCornerSize), 0F)
            lineTo(width.toFloat(), dpToPx(cutCornerSize))
            lineTo(width.toFloat(), width.toFloat())
            lineTo(0f, height.toFloat())
            close()
        }

        canvas.clipPath(rectanglePath)
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            dpToPx(cornerRadius),
            dpToPx(cornerRadius),
            customPaint
        )
        customPaint2.color = ColorUtils.blendARGB(customPaint.color, 0x000000, 0.2F)
        canvas.drawRoundRect(
            width - dpToPx(cutCornerSize),
            -50f,
            width.toFloat(),
            dpToPx(cutCornerSize) + cutCornerSize,
            dpToPx(cornerRadius - 10),
            dpToPx(cornerRadius - 10),
            customPaint2
        )

    }

    private fun dpToPx(dp: Float) = dp * resources.displayMetrics.density
}

