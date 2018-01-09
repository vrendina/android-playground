package io.levelsoftware.androidplayground.extension

import android.support.v7.widget.RecyclerView

/**
 * Return the last item position in the RecyclerView. If the RecyclerView does
 * not have an adapter -1 will be returned.
 */
fun RecyclerView.getLastPosition(): Int {
  val itemCount = this.adapter?.itemCount ?: -1
  return if (itemCount > 0) itemCount - 1 else itemCount
}