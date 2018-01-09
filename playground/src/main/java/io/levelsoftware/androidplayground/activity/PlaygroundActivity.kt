package io.levelsoftware.androidplayground.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import io.levelsoftware.androidplayground.extension.random
import io.levelsoftware.androidplayground.ui.expandinglist.ExpandingItemAnimator
import io.levelsoftware.androidplayground.ui.expandinglist.ExpandingLinearLayoutManager
import io.levelsoftware.androidplayground.ui.expandinglist.ExpandingListItemDecoration
import kotlinx.android.synthetic.main.activity_playground.recyclerView
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

    recyclerView.adapter = PlaygroundAdapter()
    recyclerView.layoutManager = ExpandingLinearLayoutManager(this)
    recyclerView.addItemDecoration(ExpandingListItemDecoration(this))
    recyclerView.itemAnimator = ExpandingItemAnimator()
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
        recyclerView.smoothScrollToPosition(0)
        return true
      }
      id.secondaryAction -> {
        val position = (0..50).random()
        Toast.makeText(this, "Scrolling to $position", Toast.LENGTH_SHORT).show()
        recyclerView.smoothScrollToPosition(position)
      }
    }
    return super.onOptionsItemSelected(item)
  }

}


