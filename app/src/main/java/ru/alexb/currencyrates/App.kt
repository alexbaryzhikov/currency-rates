package ru.alexb.currencyrates

import android.app.Application
import ru.alexb.currencyrates.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.initMainComponent()
    }
}