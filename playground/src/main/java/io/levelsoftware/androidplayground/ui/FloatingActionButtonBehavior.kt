package io.levelsoftware.androidplayground.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import levelsoftware.io.androidplayground.R

class FloatingActionButtonBehavior(
    context: Context,
    attrs: AttributeSet
) : CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {

  private val margin by lazy {
    context.resources.getDimensionPixelSize(R.dimen.fab_margin)
  }

  private val downAnimator by lazy { getAnimator() }
  private val upAnimator by lazy { getAnimator() }

  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
      child: FloatingActionButton, directTargetChild: View, target: View, axes: Int,
      type: Int): Boolean = axes == ViewCompat.SCROLL_AXIS_VERTICAL

  override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
      target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
      type: Int) {
    if (dyConsumed > 0) {
      hideFab(child)
    } else if (dyConsumed < 0) {
      showFab(child)
    }
  }

  fun hideFab(view: FloatingActionButton) {
    upAnimator.cancel()
    slide(view, downAnimator, view.height + margin.toFloat())
  }

  fun showFab(view: FloatingActionButton) {
    downAnimator.cancel()
    slide(view, upAnimator, 0f)
  }

  private fun slide(view: FloatingActionButton, animator: ObjectAnimator, targetPosition: Float) {
    if ((!animator.isStarted || !animator.isRunning) && view.y != 0f) {
      animator.target = view
      animator.setFloatValues(view.translationY, targetPosition)
      animator.start()
    }
  }

  private fun getAnimator(): ObjectAnimator {
    return ObjectAnimator().apply {
      propertyName = "translationY"
      duration = 250
      interpolator = OvershootInterpolator()
    }
  }
}
