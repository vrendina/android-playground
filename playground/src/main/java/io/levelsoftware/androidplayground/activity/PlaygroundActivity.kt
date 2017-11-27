package io.levelsoftware.androidplayground.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import levelsoftware.io.androidplayground.R
import levelsoftware.io.androidplayground.R.id
import levelsoftware.io.androidplayground.R.layout
import levelsoftware.io.androidplayground.R.string
import timber.log.Timber
import java.lang.Exception


class PlaygroundActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

  private val toolbar by lazy { findViewById<Toolbar>(id.toolbar) }
  private val bottomNavigation by lazy { findViewById<BottomNavigationView>(id.bottom_navigation) }

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
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      id.action -> {
        Timber.d("Clicked on action")
        return true
      }
    }
    return super.onOptionsItemSelected(item)
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


