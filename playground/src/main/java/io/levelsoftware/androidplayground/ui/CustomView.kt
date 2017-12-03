package io.levelsoftware.androidplayground.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import levelsoftware.io.androidplayground.R
import timber.log.Timber

class CustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

  private val fontSize = 12f
  private val textContent = "Testing"

  private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val textPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
  private val linePaint = Paint()

  init {
    backgroundPaint.color = ContextCompat.getColor(context, R.color.accent)
    textPaint.color = ContextCompat.getColor(context, R.color.white)
    linePaint.color = ContextCompat.getColor(context, R.color.primaryDark)

    textPaint.textSize = resources.displayMetrics.scaledDensity * fontSize
  }

  override fun onDraw(canvas: Canvas) {
    val canvasWidth = canvas.width
    val canvasHeight = canvas.height

    val contentWidth = canvas.width - paddingLeft - paddingRight
    val contentHeight = canvas.height - paddingTop - paddingBottom

    Timber.d("Padding $paddingLeft $paddingRight")

    val centerX = canvasWidth * 0.5f
    val centerY = canvasHeight * 0.5f

    val radius = if (contentHeight > contentWidth) contentHeight * 0.5f else contentWidth * 0.5f

    val textOffsetX = textPaint.measureText(textContent) * 0.5f
    val textOffsetY = textPaint.fontMetrics.ascent * 0.35f

    canvas.drawCircle(centerX, centerY, radius, backgroundPaint)
    canvas.drawText(textContent, centerX - textOffsetX, centerY - textOffsetY, textPaint)
    canvas.drawLine(centerX, 0f, centerX, canvasHeight.toFloat(), linePaint)
    canvas.drawLine(0f, centerY, canvasWidth.toFloat(), centerY, linePaint)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val maxTextWidth = textPaint.measureText(textContent)
    val maxTextHeight = textPaint.fontMetrics.bottom - textPaint.fontMetrics.top

    val contentWidth = maxTextWidth + paddingLeft + paddingRight
    val contentHeight = maxTextHeight + paddingTop + paddingBottom

    val contentSize = Math.round(Math.max(contentWidth, contentHeight))

    val measuredWidth = resolveSize(contentSize, widthMeasureSpec)
    val measuredHeight = resolveSize(contentSize, heightMeasureSpec)

    Timber.d("onMeasure: $measuredWidth, $measuredHeight")

    setMeasuredDimension(measuredWidth, measuredHeight)
  }
}