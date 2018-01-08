package io.levelsoftware.androidplayground.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_playground.appBarLayout
import kotlinx.android.synthetic.main.activity_playground.bottomNavigation
import kotlinx.android.synthetic.main.activity_playground.tabLayout
import kotlinx.android.synthetic.main.activity_playground.toolbar
import levelsoftware.io.androidplayground.R
import levelsoftware.io.androidplayground.R.id
import levelsoftware.io.androidplayground.R.layout
import levelsoftware.io.androidplayground.R.string
import timber.log.Timber
import java.lang.Exception


class PlaygroundActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_playground)

    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(string.app_name)
    supportActionBar?.setDisplayShowTitleEnabled(true)

    bottomNavigation.setOnNavigationItemSelectedListener(this)
    disableShiftMode(bottomNavigation)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.playground, menu)
    return true
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    Timber.d("Selected menu item ${item.title}")
    when (item.itemId) {
      R.id.action_android -> hideTabs()
      R.id.action_beach -> showTabs()
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      id.show -> {
        showTabs()
        return true
      }
      id.hide -> {
        hideTabs()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showTabs() {
//    Timber.d("Toolbar height ${toolbar.height} tablayout height ${tabLayout.height}")
//    tabLayout.translationY = 0f
//    appBarLayout.layoutParams.apply {
//      height = toolbar.height + tabLayout.height
//      Timber.d("Setting appbar height to: $height")
////      appBarLayout.layoutParams = this
//    }

    tabLayout.visibility = View.VISIBLE
  }

  private fun hideTabs() {
    val initialHeight = appBarLayout.height

    val animator = ValueAnimator().apply {
      setIntValues(initialHeight, toolbar.height)
      duration = 2000L
      addUpdateListener { animation ->
        appBarLayout.layoutParams.apply {
          height = animation.animatedValue as Int
          tabLayout.translationY = (height - initialHeight).toFloat()
          Timber.d(
              "Setting appbar height to: $height translation to ${tabLayout.translationY} y value ${tabLayout.y} height ${tabLayout.height}")
          appBarLayout.layoutParams = this
        }

      }

      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
          if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            appBarLayout.elevation = 4f * resources.displayMetrics.density
          }
        }
      })
    }
    animator.start()
  }


  // https://stackoverflow.com/a/41718722/5125812
  @SuppressLint("RestrictedApi")
  private fun disableShiftMode(view: BottomNavigationView) {
    val menuView = view.getChildAt(0) as BottomNavigationMenuView
    try {
      val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
      shiftingMode.isAccessible = true
      shiftingMode.setBoolean(menuView, false)
      shiftingMode.isAccessible = false
      for (i in 0 until menuView.childCount) {
        val item = menuView.getChildAt(i) as BottomNavigationItemView
        item.setShiftingMode(false)
        item.setChecked(item.itemData.isChecked)
      }
    } catch (e: Exception) {
      Timber.e(e)
    }
  }

}


