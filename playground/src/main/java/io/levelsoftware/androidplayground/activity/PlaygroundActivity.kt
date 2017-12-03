package io.levelsoftware.androidplayground.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_playground.mathOperationView
import kotlinx.android.synthetic.main.activity_playground.mathOperationViewFixed
import kotlinx.android.synthetic.main.activity_playground.toolbar
import levelsoftware.io.androidplayground.R
import levelsoftware.io.androidplayground.R.id
import levelsoftware.io.androidplayground.R.layout
import levelsoftware.io.androidplayground.R.string
import timber.log.Timber


class PlaygroundActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_playground)

    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(string.app_name)
    supportActionBar?.setDisplayShowTitleEnabled(true)

    mathOperationView.setValues("$250", "$125", "–")
    mathOperationViewFixed.setValues("$250,000", "$500,000,000", "÷")
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
        mathOperationView.setValues("$150,000", "$1,500,000,000")
        mathOperationViewFixed.setValues("$150,000", "$1,500,000,000")
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

}


