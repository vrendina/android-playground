package io.levelsoftware.androidplayground.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import levelsoftware.io.androidplayground.R.styleable

class DynamicHeightImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context,
    attrs) {

  private val defaultAspectRatio = 2.5f

  private var aspectRatio: Float = defaultAspectRatio
  private var minHeight: Int = 0

  init {
    val typedArray = context.obtainStyledAttributes(attrs,
        styleable.DynamicHeightImageView)

    aspectRatio = typedArray.getFloat(
        styleable.DynamicHeightImageView_aspectRatio,
        defaultAspectRatio)
    minHeight = typedArray.getDimensionPixelSize(
        styleable.DynamicHeightImageView_minHeight, 0)

    typedArray.recycle()
  }

  override fun onMeasure(widthSpec: Int, heightSpec: Int) {
    super.onMeasure(widthSpec, heightSpec)

    var height = (measuredWidth / aspectRatio).toInt()
    if (height < minHeight) height = minHeight

    setMeasuredDimension(measuredWidth, height)
  }

}