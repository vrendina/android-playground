package io.levelsoftware.androidplayground.ui.expandinglist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import levelsoftware.io.androidplayground.R
import kotlin.LazyThreadSafetyMode.NONE

class ExpandingViewHolder(itemView: View,
    listener: OnExpandingViewClickListener) : RecyclerView.ViewHolder(itemView) {

  val titleTextView: TextView by lazy(NONE) { itemView.findViewById<TextView>(R.id.titleTextView) }
  val expandingTextView: TextView by lazy(NONE) {
    itemView.findViewById<TextView>(R.id.expandingTextView)
  }
  val indicatorImageView: ImageView by lazy(NONE) {
    itemView.findViewById<ImageView>(R.id.expandingImageView)
  }

  var expanded = false
    set(expanded) {
      field = expanded
      /*
        Reset the LayoutParams height to WRAP_CONTENT. When the animation runs it sets the
        height to an exact value so it doesn't get measured correctly when add or removing
        the expanding TextView on the next pass.
       */
      itemView.layoutParams.apply {
        height = LayoutParams.WRAP_CONTENT
        itemView.layoutParams = this
      }

      if (expanded) {
        indicatorImageView.rotation = 180f
        expandingTextView.alpha = 1f
        expandingTextView.visibility = View.VISIBLE
      } else {
        indicatorImageView.rotation = 0f
        expandingTextView.alpha = 0f
        expandingTextView.visibility = View.GONE
      }
    }

  init {
    itemView.setOnClickListener { listener.onToggleExpanded(adapterPosition) }
  }

  interface OnExpandingViewClickListener {
    fun onToggleExpanded(position: Int)
    fun onClickLink()
  }
}
