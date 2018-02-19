package io.levelsoftware.androidplayground.ui

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.math.MathUtils
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import kotlin.LazyThreadSafetyMode.NONE

class SwipeMenuLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

  private val dragHelper = ViewDragHelper.create(this, 1.0f, DragHelperCallback())

  private val backgroundView by lazy(NONE) {
    getChildAt(0) ?: throw IllegalStateException("Background view not found")
  }
  private val foregroundView by lazy(NONE) {
    getChildAt(1) ?: throw IllegalStateException("Foreground view not found")
  }

  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    return if (!dragHelper.shouldInterceptTouchEvent(event)) {
      super.onInterceptTouchEvent(event)
    } else true
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    dragHelper.processTouchEvent(event)
    return true
  }

  /**
   * Close the swipe menu by moving the foreground view back to the initial position.
   */
  fun closeMenu() {
    if (dragHelper.smoothSlideViewTo(foregroundView, 0, 0)) {
      ViewCompat.postOnAnimation(foregroundView, SettleRunnable(foregroundView))
    }
  }

  private fun shouldOpen(xvel: Float): Boolean {
    // Apply some friction to the xvel and determine where the view would end up
    return -(foregroundView.x + xvel * 0.1) > backgroundView.width / 2
  }

  private inner class DragHelperCallback : ViewDragHelper.Callback() {
    override fun tryCaptureView(child: View, pointerId: Int): Boolean {
      return child == foregroundView
    }

    override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
      return MathUtils.clamp(left, -width, 0)
    }

    override fun getViewHorizontalDragRange(child: View): Int = backgroundView.width

    override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
      // Determine the final position of the view based on whether or not it should be opened
      val finalPosition = if (shouldOpen(xvel)) -backgroundView.width else 0

      if (dragHelper.settleCapturedViewAt(finalPosition, 0)) {
        ViewCompat.postOnAnimation(releasedChild, SettleRunnable(releasedChild))
      }
    }
  }

  private inner class SettleRunnable(private val view: View) : Runnable {
    override fun run() {
      if (dragHelper.continueSettling(true)) {
        ViewCompat.postOnAnimation(view, this)
      }
    }
  }
}