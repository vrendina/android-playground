package io.levelsoftware.androidplayground.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.levelsoftware.androidplayground.activity.PlaygroundChildAdapter.PlaygroundChildViewHolder
import levelsoftware.io.androidplayground.R
import kotlin.LazyThreadSafetyMode.NONE

class PlaygroundChildAdapter : RecyclerView.Adapter<PlaygroundChildViewHolder>() {

  var count: Int = 0
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onBindViewHolder(holder: PlaygroundChildViewHolder, position: Int) {
    holder.textView.text = "Subposition $position"
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaygroundChildViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.list_item_child, parent, false)

    return PlaygroundChildViewHolder(view)

  }

  override fun getItemCount(): Int = count

  inner class PlaygroundChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textView: TextView by lazy(NONE) { itemView.findViewById<TextView>(R.id.textView) }

  }

}