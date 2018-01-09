package io.levelsoftware.androidplayground.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.levelsoftware.androidplayground.ui.expandinglist.ExpandingViewHolder
import io.levelsoftware.androidplayground.ui.expandinglist.ExpandingViewHolder.OnExpandingViewClickListener
import levelsoftware.io.androidplayground.R

class PlaygroundAdapter : RecyclerView.Adapter<ExpandingViewHolder>(), OnExpandingViewClickListener {

  private val expandedPositions = HashSet<Int>()

  override fun onBindViewHolder(holder: ExpandingViewHolder, position: Int) {
    holder.titleTextView.text = "Position $position"
    holder.expanded = expandedPositions.contains(position)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandingViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.list_item, parent, false)

    return ExpandingViewHolder(view, this)
  }

  override fun getItemCount(): Int = 50

  override fun onToggleExpanded(position: Int) {
    val expanded = expandedPositions.contains(position)
    if (expanded) expandedPositions.remove(position) else expandedPositions.add(position)
    notifyItemChanged(position)
  }

  override fun onClickLink() {
    TODO("not implemented")
  }

}