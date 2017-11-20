package levelsoftware.io.androidplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import timber.log.Timber

class Playground : AppCompatActivity() {

  private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_playground)

    setSupportActionBar(toolbar)
    supportActionBar?.title = getString(R.string.app_name)
    supportActionBar?.setDisplayShowTitleEnabled(true)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.playground, menu)
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
