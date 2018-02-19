package io.levelsoftware.androidplayground.dagger

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
  fun inject(context: Application)
}