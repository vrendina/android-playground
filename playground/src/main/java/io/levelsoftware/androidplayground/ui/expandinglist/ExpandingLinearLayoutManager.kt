package io.levelsoftware.androidplayground.ui.expandinglist

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State

class ExpandingLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

  override fun supportsPredictiveItemAnimations(): Boolean = true

  override fun smoothScrollToPosition(recyclerView: RecyclerView, state: State, position: Int) {
    CustomSmoothScroller(recyclerView.context).apply {
      targetPosition = position
      startSmoothScroll(this)
    }
  }

  private inner class CustomSmoothScroller(context: Context) : LinearSmoothScroller(context) {

    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int,
        snapPreference: Int): Int {
      return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
    }
  }
}