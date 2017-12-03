package io.levelsoftware.androidplayground.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import levelsoftware.io.androidplayground.R

class MathOperationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

  private var top = "0"
  private var bottom = "0"
  private var sign = "+"

  private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

  private val lineThickness: Float
  private val textHeight by lazy { textPaint.fontMetrics.bottom - textPaint.fontMetrics.top }

  private val defaultTextColor = Color.BLACK
  private val defaultLineColor = Color.BLACK

  private val defaultTextSize = 12f * resources.displayMetrics.scaledDensity
  private val defaultLineThickness = 2f * resources.displayMetrics.density

  init {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MathOperationView)

    textPaint.color = typedArray.getColor(R.styleable.MathOperationView_textColor, defaultTextColor)
    linePaint.color = typedArray.getColor(R.styleable.MathOperationView_lineColor, defaultLineColor)

    textPaint.textSize = typedArray.getDimension(R.styleable.MathOperationView_textSize,
        defaultTextSize)
    lineThickness = typedArray.getDimension(R.styleable.MathOperationView_lineThickness,
        defaultLineThickness)

    typedArray.recycle()
  }

  fun setValues(top: String = this.top, bottom: String = this.bottom, sign: String = this.sign) {
    this.top = top
    this.bottom = bottom
    this.sign = sign
    requestLayout()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val topTextWidth = textPaint.measureText(top)
    val bottomTextWidth = textPaint.measureText(bottom) + textPaint.measureText(
        sign) * 4f + 2f * textPaint.fontMetrics.bottom

    val contentHeight = Math.round(2f * textHeight + lineThickness + paddingTop + paddingBottom)
    val contentWidth = Math.round(
        Math.max(topTextWidth, bottomTextWidth) + paddingLeft + paddingRight)

    val measuredWidth = resolveSize(contentWidth, widthMeasureSpec)
    val measuredHeight = resolveSize(contentHeight, heightMeasureSpec)

    setMeasuredDimension(measuredWidth, measuredHeight)
  }

  override fun onDraw(canvas: Canvas) {
    val centerX = canvas.width * 0.5f

    val lineTop = Math.round(canvas.height - paddingBottom - lineThickness)
    val lineBottom = canvas.height - paddingBottom

    val signWidth = textPaint.measureText(sign)

    val availableTextWidth = Math.round(
        canvas.width - paddingLeft - paddingRight - 2f * textPaint.fontMetrics.bottom - 4f * signWidth)

    val bottomSized = ellipsize(bottom, availableTextWidth)
    val topSized = ellipsize(top, availableTextWidth)

    val bottomTextX = Math.round(centerX - textPaint.measureText(bottomSized) * 0.5f)
    val bottomTextY = Math.round(
        canvas.height - paddingBottom - lineThickness - textPaint.fontMetrics.bottom)

    val topTextX = Math.round(centerX - textPaint.measureText(topSized) * 0.5f)
    val topTextY = bottomTextY - textHeight

    val signTextX = textPaint.fontMetrics.bottom + paddingLeft
    val signTextY = lineTop - textPaint.fontMetrics.bottom

    canvas.drawText(bottomSized, bottomTextX.toFloat(), bottomTextY.toFloat(), textPaint)
    canvas.drawText(topSized, topTextX.toFloat(), topTextY, textPaint)
    canvas.drawText(sign, signTextX, signTextY, textPaint)

    canvas.drawRect(paddingLeft.toFloat(), lineTop.toFloat(),
        (canvas.width - paddingRight).toFloat(), lineBottom.toFloat(), linePaint)

  }

  private fun ellipsize(string: String, availableWidth: Int): String {
    if (textPaint.measureText(string) > availableWidth) {
      return when {
        string.length == 1 -> ""
        else -> ellipsize(string.substring(0, string.length - 2).plus("…"), availableWidth)
      }
    }
    return string
  }
}