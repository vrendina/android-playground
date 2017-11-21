package levelsoftware.io.androidplayground

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

class DynamicHeightImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context,
    attrs) {

  private val defaultAspectRatio = 2.5f

  private var aspectRatio: Float = defaultAspectRatio
  private var minHeight: Int = 0

  init {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DynamicHeightImageView)

    aspectRatio = typedArray.getFloat(R.styleable.DynamicHeightImageView_aspectRatio,
        defaultAspectRatio)
    minHeight = typedArray.getDimensionPixelSize(R.styleable.DynamicHeightImageView_minHeight, 0)

    typedArray.recycle()
  }

  override fun onMeasure(widthSpec: Int, heightSpec: Int) {
    super.onMeasure(widthSpec, heightSpec)

    var height = (measuredWidth / aspectRatio).toInt()
    if (height < minHeight) height = minHeight

    setMeasuredDimension(measuredWidth, height)
  }

}