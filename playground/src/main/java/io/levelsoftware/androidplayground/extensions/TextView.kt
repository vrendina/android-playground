package io.levelsoftware.androidplayground.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.widget.TextView

fun TextView.setCompatDrawable(
    context: Context, @DrawableRes resId: Int, @ColorRes tint: Int? = null) {
  AppCompatResources.getDrawable(context, resId)?.also { drawable ->

    if (tint != null) {
      val color = ContextCompat.getColor(context, tint)
      drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
  }
}
