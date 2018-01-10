package io.levelsoftware.androidplayground.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.levelsoftware.androidplayground.ui.CustomURLSpan.OnInlineLinkClickListener
import io.levelsoftware.androidplayground.ui.expandinglist.ExpandingViewHolder
import io.levelsoftware.androidplayground.ui.expandinglist.ExpandingViewHolder.OnExpandingViewClickListener
import levelsoftware.io.androidplayground.R

class PlaygroundAdapter(private val linkListener: OnInlineLinkClickListener)
  : RecyclerView.Adapter<ExpandingViewHolder>(), OnExpandingViewClickListener {

  private val expandedPositions = HashSet<Int>()

  override fun onBindViewHolder(holder: ExpandingViewHolder, position: Int) {
    holder.titleTextView.text = "Position $position"
    holder.setHtmlContent(R.raw.test_content)
    holder.expanded = expandedPositions.contains(position)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandingViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.list_item, parent, false)
    return ExpandingViewHolder(view, this, linkListener)
  }

  override fun getItemCount(): Int = 50

  override fun toggleExpanded(position: Int) {
    val expanded = expandedPositions.contains(position)
    setExpanded(position, !expanded)
  }

  fun setExpanded(position: Int, expand: Boolean) {
    if (expand) expandedPositions.add(position) else expandedPositions.remove(position)
    notifyItemChanged(position)
  }
}