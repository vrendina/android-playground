package io.levelsoftware.androidplayground.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.levelsoftware.androidplayground.activity.PlaygroundAdapter.PlaygroundContainerViewHolder
import io.levelsoftware.androidplayground.extension.random
import levelsoftware.io.androidplayground.R
import kotlin.LazyThreadSafetyMode.NONE

class PlaygroundAdapter : RecyclerView.Adapter<PlaygroundContainerViewHolder>() {

  private val childCounts by lazy { (0 until itemCount).mapTo(ArrayList(), { (1..5).random() }) }
  private val sharedPool = RecyclerView.RecycledViewPool()

  override fun onBindViewHolder(holder: PlaygroundContainerViewHolder, position: Int) {
    holder.textView.text = "Position $position"
    holder.setChildCount(childCounts[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaygroundContainerViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.list_item_container, parent, false)

    return PlaygroundContainerViewHolder(view)
  }

  override fun getItemCount(): Int = 50

  inner class PlaygroundContainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView: TextView by lazy(NONE) { itemView.findViewById<TextView>(R.id.textView) }

    private val recyclerView: RecyclerView by lazy(NONE) {
      itemView.findViewById<RecyclerView>(R.id.recyclerView)
    }
    private val adapter = PlaygroundChildAdapter()

    init {
      recyclerView.layoutManager = LinearLayoutManager(itemView.context,
          LinearLayoutManager.VERTICAL, false)
      recyclerView.adapter = adapter
      recyclerView.recycledViewPool = sharedPool
    }

    fun setChildCount(count: Int) {
      adapter.count = count
    }
  }

}