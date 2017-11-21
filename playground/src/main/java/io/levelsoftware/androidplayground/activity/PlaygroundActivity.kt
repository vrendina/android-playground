package io.levelsoftware.androidplayground.activity

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import io.levelsoftware.androidplayground.ui.hide
import io.levelsoftware.androidplayground.ui.show
import levelsoftware.io.androidplayground.R
import levelsoftware.io.androidplayground.R.id
import levelsoftware.io.androidplayground.R.layout
import levelsoftware.io.androidplayground.R.string
import timber.log.Timber

class PlaygroundActivity : AppCompatActivity(), OnNavigationItemSelectedListener, AppBarLayout.OnOffsetChangedListener {

  private val toolbar by lazy { findViewById<Toolbar>(id.toolbar) }
  private val appBarLayout by lazy { findViewById<AppBarLayout>(id.app_bar_layout) }
  private val bottomNavigation by lazy { findViewById<BottomNavigationView>(id.bottom_navigation) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_playground)

    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(string.app_name)
    supportActionBar?.setDisplayShowTitleEnabled(true)

    appBarLayout.addOnOffsetChangedListener(this)
    bottomNavigation.setOnNavigationItemSelectedListener(this)
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

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    val maxScroll = appBarLayout?.totalScrollRange ?: 0

    var percentage = 1 - Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()
    percentage = if (percentage < 0) 0f else percentage

    Timber.d("Percentage: $percentage")

    if (percentage > 0f) bottomNavigation.show()
    if (percentage == 0f) bottomNavigation.hide()
  }
}


