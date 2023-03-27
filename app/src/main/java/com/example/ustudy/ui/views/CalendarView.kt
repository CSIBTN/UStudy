package com.example.ustudy.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

private const val CALENDAR_COLUMNS = 7
private const val CALENDAR_ROWS = 5

class CalendarView(
    context: Context, attributeSet: AttributeSet
) : View(context, attributeSet) {


    var onDateClickedCallback: (Int) -> Unit = {}

    private val strokeWidthF = 15F
    private val rectPaint = Paint()
    private val linePaint = Paint()
    private val textPaint = Paint()

    private var heightStep = 0
    private var widthStep = 0
    private val textHeight: Float = dpToPx(18F)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        heightStep = height / CALENDAR_ROWS
        widthStep = width / CALENDAR_COLUMNS
        rectPaint.apply {
            strokeWidth = strokeWidthF
            color = Color.BLACK
        }
        linePaint.color = Color.RED
        linePaint.strokeWidth = strokeWidthF
        textPaint.apply {
            color = Color.WHITE
            textSize = textHeight
            isFakeBoldText = true
        }
        canvas.drawRoundRect(
            0F, 0F, width.toFloat(), height.toFloat(), 25F, 25F, rectPaint
        )
        for (i in 1 until CALENDAR_ROWS) {
            canvas.drawLine(
                0F,
                (i * heightStep).toFloat(),
                width.toFloat(),
                (i * heightStep).toFloat(),
                linePaint
            )
        }
        for (i in 1 until CALENDAR_COLUMNS) {
            canvas.drawLine(
                (i * widthStep).toFloat(),
                0F,
                (i * widthStep).toFloat(),
                height.toFloat(),
                linePaint
            )
        }
        for (i in 0..30) {
            val textPositionX = widthStep * (i % CALENDAR_COLUMNS) + strokeWidthF
            val textPositionY = (i / CALENDAR_COLUMNS) * heightStep + textHeight + strokeWidthF / 2
            canvas.drawText("${i + 1}", textPositionX, textPositionY, textPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val offsetX = event.x
                val offsetY = event.y
                val column = (offsetX / width * CALENDAR_COLUMNS).toInt() + 1
                val row = (offsetY / height * CALENDAR_ROWS).toInt() + 1
                val day = column + (row - 1) * CALENDAR_COLUMNS
                if (day <= 31) {
                    onDateClickedCallback(day)
                }
            }
        }
        return true
    }

    private fun dpToPx(dp: Float) =
        dp * resources.displayMetrics.density

}