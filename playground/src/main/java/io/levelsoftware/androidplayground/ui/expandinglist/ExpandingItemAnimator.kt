package io.levelsoftware.androidplayground.ui.expandinglist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView.State
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View

class ExpandingItemAnimator : DefaultItemAnimator() {

  private val activeAnimators = arrayListOf<AnimatorSet>()
  private val transitionDuration = 600L

  override fun canReuseUpdatedViewHolder(viewHolder: ViewHolder,
      payloads: MutableList<Any>): Boolean {
    return true
  }

  override fun recordPreLayoutInformation(state: State, viewHolder: ViewHolder, changeFlags: Int,
      payloads: MutableList<Any>): ItemHolderInfo {
    return recordItemHolderInfo(viewHolder)
  }

  override fun recordPostLayoutInformation(state: State, viewHolder: ViewHolder): ItemHolderInfo {
    return recordItemHolderInfo(viewHolder)
  }

  override fun animateChange(oldHolder: ViewHolder, newHolder: ViewHolder, preInfo: ItemHolderInfo,
      postInfo: ItemHolderInfo): Boolean {

    if (newHolder is ExpandingViewHolder
        && preInfo is ExpandingHolderInfo
        && postInfo is ExpandingHolderInfo) {

      // Animation to fade in/out the expanded content
      val fade = ObjectAnimator.ofFloat(newHolder.expandingTextView, View.ALPHA,
          preInfo.alpha,
          postInfo.alpha)

      // Animation to rotate the indicator arrow
      val rotate = ObjectAnimator.ofFloat(newHolder.indicatorImageView, View.ROTATION,
          preInfo.rotation,
          postInfo.rotation)

      // Animation to change the height of the itemView
      val height = ValueAnimator().apply {
        setIntValues(preInfo.height, postInfo.height)
        addUpdateListener { animation ->
          val params = newHolder.itemView.layoutParams
          params.height = animation.animatedValue as Int
          newHolder.itemView.layoutParams = params
        }
      }

      if (!newHolder.expanded) {
        // If the view is contracting the expanded content needs to be visible so it can fade out
        fade.addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationStart(animation: Animator) {
            newHolder.expandingTextView.visibility = View.VISIBLE
          }
        })
      }

      val animatorSet = AnimatorSet()
      activeAnimators.add(animatorSet)

      animatorSet.apply {
        playTogether(fade, rotate, height)
        addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator) {
            removeListener(this)
            activeAnimators.remove(animatorSet)
            dispatchChangeFinished(newHolder, false)
          }

          override fun onAnimationCancel(animation: Animator) {
            super.onAnimationCancel(animation)
            removeListener(this)
            activeAnimators.remove(animatorSet)
            dispatchChangeFinished(newHolder, false)
          }
        })
        duration = changeDuration
        interpolator = FastOutSlowInInterpolator()
        start()
      }
    }

    return false
  }

  override fun endAnimations() {
    val animators = arrayListOf<AnimatorSet>()
    animators.addAll(activeAnimators)
    animators.forEach { animator -> animator.cancel() }

    super.endAnimations()
  }

  override fun isRunning(): Boolean {
    return activeAnimators.isNotEmpty() || super.isRunning()
  }

  /*
      Move animations are not necessary because they are automatically handled as the expanding
      item changes bounds.
     */
  override fun animateMove(holder: ViewHolder, fromX: Int, fromY: Int, toX: Int,
      toY: Int): Boolean = false

  override fun getRemoveDuration(): Long = transitionDuration
  override fun getMoveDuration(): Long = transitionDuration
  override fun getAddDuration(): Long = transitionDuration
  override fun getChangeDuration(): Long = transitionDuration

  /*
    Records the state of the view holder in the ExpandingHolderInfo data class.
   */
  private fun recordItemHolderInfo(viewHolder: ViewHolder): ItemHolderInfo {
    return when (viewHolder) {
      is ExpandingViewHolder -> {
        ExpandingHolderInfo(viewHolder.itemView.height,
            viewHolder.indicatorImageView.rotation,
            viewHolder.expandingTextView.alpha)
            .setFrom(viewHolder)
      }
      else -> ItemHolderInfo().setFrom(viewHolder)
    }
  }

  data class ExpandingHolderInfo(val height: Int,
      val rotation: Float,
      val alpha: Float) : ItemHolderInfo()
}