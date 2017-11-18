package levelsoftware.io.androidplayground

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

class PlaygroundApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    if (LeakCanary.isInAnalyzerProcess(this)) return
    LeakCanary.install(this)

    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this)
    }
  }
}