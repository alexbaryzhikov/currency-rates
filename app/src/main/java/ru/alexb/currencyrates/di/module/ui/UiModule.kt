package ru.alexb.currencyrates.di.module.ui

import dagger.Module
import dagger.Provides
import ru.alexb.currencyrates.di.annotation.scope.UiScope
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import ru.alexb.currencyrates.ui.controller.MainViewController
import ru.alexb.currencyrates.ui.controller.MainViewControllerImpl

@Module
class UiModule {

    @Provides
    @UiScope
    fun provideMainViewController(service: CurrencyRatesService): MainViewController {
        return MainViewControllerImpl(service)
    }
}