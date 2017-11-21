package io.levelsoftware.androidplayground.ui

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.util.AttributeSet

class FixedAppBarLayoutBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.Behavior(
    context, attrs) {

  init {
    setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
      /**
       * Allows control over whether the given [AppBarLayout] can be dragged or not.
       *
       *
       * Dragging is defined as a direct touch on the AppBarLayout with movement. This
       * call does not affect any nested scrolling.
       *
       * @return true if we are in a position to scroll the AppBarLayout via a drag, false
       * if not.
       */
      override fun canDrag(appBarLayout: AppBarLayout): Boolean = false
    })
  }
}
