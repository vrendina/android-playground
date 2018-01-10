package io.levelsoftware.androidplayground.ui.expandinglist

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.support.annotation.RawRes
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import io.levelsoftware.androidplayground.ui.CustomURLSpan
import io.levelsoftware.androidplayground.ui.CustomURLSpan.OnInlineLinkClickListener
import levelsoftware.io.androidplayground.R
import kotlin.LazyThreadSafetyMode.NONE

class ExpandingViewHolder(itemView: View,
    expandListener: OnExpandingViewClickListener,
    private val linkListener: OnInlineLinkClickListener) : RecyclerView.ViewHolder(itemView) {

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
    expandingTextView.movementMethod = LinkMovementMethod.getInstance()

    itemView.setOnClickListener { expandListener.toggleExpanded(adapterPosition) }
  }

  fun setHtmlContent(@RawRes resId: Int) {
    val inputStream = itemView.context.resources.openRawResource(resId)
    val content = inputStream.bufferedReader().use { it.readText() }

    val sequence = if (VERSION.SDK_INT >= VERSION_CODES.N) {
      Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
    } else {
      Html.fromHtml(content)
    }
    val builder = SpannableStringBuilder(sequence)
    val urls = builder.getSpans(0, sequence.length, URLSpan::class.java)
    urls.forEach { span ->
      builder.removeSpan(span)
      sequence.apply {
        builder.setSpan(CustomURLSpan(span.url, linkListener),
            getSpanStart(span), getSpanEnd(span), getSpanFlags(span))
      }
    }

    expandingTextView.text = builder
  }

  interface OnExpandingViewClickListener {
    fun toggleExpanded(position: Int)
  }
}
