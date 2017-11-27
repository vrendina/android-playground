package io.levelsoftware.androidplayground.activity

import android.os.Bundle
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


class PlaygroundActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

  private val toolbar by lazy { findViewById<Toolbar>(id.toolbar) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_playground)

    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(string.app_name)
    supportActionBar?.setDisplayShowTitleEnabled(true)
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

}


