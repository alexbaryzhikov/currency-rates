package ru.alexb.currencyrates.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(context: Context) {
    private val appContext: Context = context.applicationContext

    @Provides
    fun provideContext(): Context {
        return appContext
    }
}