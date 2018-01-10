package io.levelsoftware.androidplayground.ui

import android.text.style.URLSpan
import android.view.View

class CustomURLSpan(url: String, private val listener: OnInlineLinkClickListener) : URLSpan(url) {

  override fun onClick(widget: View) = listener.onLinkClick(url)

  interface OnInlineLinkClickListener {
    fun onLinkClick(url: String)
  }
}