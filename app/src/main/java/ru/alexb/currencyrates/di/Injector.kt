package ru.alexb.currencyrates.di

import org.jetbrains.annotations.TestOnly
import ru.alexb.currencyrates.di.component.DaggerMainComponent
import ru.alexb.currencyrates.di.component.MainComponent
import ru.alexb.currencyrates.di.component.UiComponent
import ru.alexb.currencyrates.di.module.ui.UiModule
import ru.alexb.currencyrates.ui.view.MainActivity

object Injector {
    private var mainComponent: MainComponent? = null
    private var uiComponent: UiComponent? = null

    fun mainComponent(): MainComponent {
        if (mainComponent == null) {
            mainComponent = DaggerMainComponent.builder().build()
        }
        return mainComponent!!
    }

    @TestOnly
    fun setMainComponent(component: MainComponent) {
        mainComponent = component
    }

    fun initUiComponent(mainActivity: MainActivity) {
        uiComponent = mainComponent().uiComponent(UiModule(mainActivity))
    }

    fun invalidateUiComponent() {
        uiComponent = null
    }

    fun uiComponent(): UiComponent {
        return uiComponent ?: error("Call initUiComponent() first")
    }

    @TestOnly
    fun setUiComponent(component: UiComponent) {
        uiComponent = component
    }
}