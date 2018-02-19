package io.levelsoftware.androidplayground.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Application) {

  @Provides
  @Singleton
  fun provideContext() = context

}