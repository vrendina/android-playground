package io.levelsoftware.androidplayground.ui.expandinglist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.view.View
import io.levelsoftware.androidplayground.extension.getLastPosition
import levelsoftware.io.androidplayground.R
import kotlin.LazyThreadSafetyMode.NONE

class ExpandingListItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

  private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

  private val innerLineThickness by lazy(NONE) {
    context.resources.getDimensionPixelSize(R.dimen.inner_separator_thickness)
  }

  private val outerLineThickness by lazy(NONE) {
    context.resources.getDimensionPixelSize(R.dimen.outer_separator_thickness)
  }

  init {
    innerPaint.color = ContextCompat.getColor(context, R.color.innerListDivider)
    outerPaint.color = ContextCompat.getColor(context, R.color.outerListDivider)
  }

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
    super.getItemOffsets(outRect, view, parent, state)
    val position = parent.getChildAdapterPosition(view)

    // Add space for the top separator
    if (position == 0) {
      outRect.top = outerLineThickness
    }

    // Add space for the bottom separator
    if (position < parent.getLastPosition()) {
      outRect.bottom = innerLineThickness
    } else if (position == parent.getLastPosition()) {
      outRect.bottom = outerLineThickness
    }
  }

  override fun onDraw(canvas: Canvas, parent: RecyclerView, state: State) {
    super.onDraw(canvas, parent, state)

    val lastPosition = parent.getLastPosition()

    for (index in 0 until parent.childCount) {
      parent.getChildAt(index).let { child ->
        val position = parent.getChildAdapterPosition(child)

        // Draw the top separator
        if (position == 0) {
          val top = child.y - outerLineThickness
          canvas.drawRect(0f, top, canvas.width.toFloat(), child.y, outerPaint)
        }

        // Draw the bottom separator
        val top = child.y + child.height
        if (position < lastPosition) {
          canvas.drawRect(0f, top, canvas.width.toFloat(), top + innerLineThickness, innerPaint)
        } else if (position == lastPosition) {
          canvas.drawRect(0f, top, canvas.width.toFloat(), top + outerLineThickness, outerPaint)
        }
      }
    }
  }


}