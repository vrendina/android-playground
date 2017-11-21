package io.levelsoftware.androidplayground.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import levelsoftware.io.androidplayground.R
import timber.log.Timber

fun BottomNavigationView.hide() {
  val params = this.layoutParams as CoordinatorLayout.LayoutParams
  if (this.layoutParams is CoordinatorLayout.LayoutParams) {
    val behavior = params.behavior
    if (behavior is BottomNavigationViewBehavior) {
      behavior.hideNavigationView(this)
    }
  }
}

fun BottomNavigationView.show() {
  val params = this.layoutParams as CoordinatorLayout.LayoutParams
  if (this.layoutParams is CoordinatorLayout.LayoutParams) {
    val behavior = params.behavior
    if (behavior is BottomNavigationViewBehavior) {
      behavior.showNavigationView(this)
    }
  }
}

class BottomNavigationViewBehavior(private val context: Context,
    attrs: AttributeSet) : CoordinatorLayout.Behavior<BottomNavigationView>(context, attrs) {

  private val animationDuration = 300L

  private var initialY = 0f
  private var finalY = 0f

  private var initialParentHeight = 0

  private val height by lazy {
    context.resources.getDimensionPixelSize(R.dimen.bottom_navigation_height)
  }

  private val downAnimator by lazy { getAnimator() }
  private val upAnimator by lazy { getAnimator() }

  override fun onLayoutChild(parent: CoordinatorLayout?, child: BottomNavigationView?,
      layoutDirection: Int): Boolean {

    val parentHeight = parent?.height ?: 0
    if (initialParentHeight == 0) {
      initialParentHeight = parentHeight

      initialY = (initialParentHeight - height).toFloat()
      finalY = initialParentHeight.toFloat()
    }

    if (parentHeight < initialParentHeight) {
      /*
       * If the view has gotten smaller than the initial height, hide bar. This is so the bar
       * is hidden when the keyboard opens.
       */
      Timber.d("Hiding the bar: $parentHeight $initialParentHeight")
      child?.let { child.hide() }
    }
    Timber.d("Called onLayoutChild: current -- $parentHeight initial -- $initialParentHeight")

    return super.onLayoutChild(parent, child, layoutDirection)
  }

  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
      child: BottomNavigationView, directTargetChild: View, target: View, axes: Int,
      type: Int): Boolean = axes == ViewCompat.SCROLL_AXIS_VERTICAL

  override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: BottomNavigationView,
      target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
      type: Int) {
    if (dyConsumed > 0) {
      hideNavigationView(child)
    } else if (dyConsumed < 0) {
      showNavigationView(child)
    }
  }

  fun hideNavigationView(view: BottomNavigationView) {
    upAnimator.cancel()
    slide(view, downAnimator, finalY)
  }

  fun showNavigationView(view: BottomNavigationView) {
    downAnimator.cancel()
    slide(view, upAnimator, initialY)
  }

  private fun slide(view: BottomNavigationView, animator: ObjectAnimator, targetPosition: Float) {
    if ((!animator.isStarted || !animator.isRunning) && view.y != 0f) {
      animator.target = view
      animator.setFloatValues(view.y, targetPosition)
      animator.start()
    }
  }

  private fun getAnimator(): ObjectAnimator {
    val anim = ObjectAnimator()
    anim.propertyName = "y"
    anim.duration = animationDuration
    anim.interpolator = FastOutSlowInInterpolator()
    return anim
  }
}