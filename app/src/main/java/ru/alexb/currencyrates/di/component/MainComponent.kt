package ru.alexb.currencyrates.di.component

import dagger.Component
import ru.alexb.currencyrates.di.module.AppModule
import ru.alexb.currencyrates.di.module.MainModule
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        MainModule::class
    ]
)
@Singleton
interface MainComponent {

    fun rates(): RatesComponent
}
