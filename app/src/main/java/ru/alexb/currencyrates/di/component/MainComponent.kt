package ru.alexb.currencyrates.di.component

import dagger.Component
import ru.alexb.currencyrates.di.module.MainModule
import ru.alexb.currencyrates.di.module.ui.UiModule
import javax.inject.Singleton

@Component(
    modules = [
        MainModule::class
    ]
)
@Singleton
interface MainComponent {

    fun ratesComponent(): RatesComponent

    fun uiComponent(uiModule: UiModule): UiComponent
}
