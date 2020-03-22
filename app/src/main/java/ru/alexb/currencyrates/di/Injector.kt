package ru.alexb.currencyrates.di

import android.content.Context
import ru.alexb.currencyrates.di.component.DaggerMainComponent
import ru.alexb.currencyrates.di.component.MainComponent
import ru.alexb.currencyrates.di.module.AppModule

object Injector {
    lateinit var mainComponent: MainComponent

    fun initialize(context: Context) {
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}