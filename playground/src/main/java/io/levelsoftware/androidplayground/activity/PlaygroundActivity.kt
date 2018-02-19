package io.levelsoftware.androidplayground.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.levelsoftware.androidplayground.extensions.setCompatDrawable
import kotlinx.android.synthetic.main.activity_playground.actionButton
import kotlinx.android.synthetic.main.activity_playground.foregroundContainer
import kotlinx.android.synthetic.main.activity_playground.swipeMenuLayout
import kotlinx.android.synthetic.main.activity_playground.toolbar
import levelsoftware.io.androidplayground.R
import timber.log.Timber


class PlaygroundActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_playground)

    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(R.string.app_name)
    supportActionBar?.setDisplayShowTitleEnabled(true)

    actionButton.setOnClickListener {
      Timber.d("Clicked on action button")
      swipeMenuLayout.closeMenu()
    }
    foregroundContainer.setOnClickListener {
      Timber.d("Clicked on foreground")
      swipeMenuLayout.closeMenu()
    }

    actionButton.setCompatDrawable(this, R.drawable.ic_delete_sweep_black_24dp, R.color.white)
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
      R.id.action -> {
        Timber.d("Clicked on action")
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

}


