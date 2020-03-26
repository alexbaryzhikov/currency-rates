package ru.alexb.currencyrates.di

import ru.alexb.currencyrates.di.component.DaggerMainComponent
import ru.alexb.currencyrates.di.component.MainComponent
import ru.alexb.currencyrates.di.component.UiComponent
import ru.alexb.currencyrates.di.module.ui.UiModule
import ru.alexb.currencyrates.ui.view.MainActivity

object Injector {
    lateinit var mainComponent: MainComponent
    var uiComponent: UiComponent? = null

    fun initMainComponent() {
        mainComponent = DaggerMainComponent.builder().build()
    }

    fun initUiComponent(mainActivity: MainActivity) {
        uiComponent = mainComponent.uiComponent(UiModule(mainActivity))
    }

    fun uiComponent(): UiComponent {
        return uiComponent ?: error("Call iniUiComponent() first")
    }

    fun invalidateUiComponent() {
        uiComponent = null
    }
}