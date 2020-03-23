package ru.alexb.currencyrates.di.module

import dagger.Module
import dagger.Provides
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import ru.alexb.currencyrates.rates.service.CurrencyRatesServiceImpl
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
    @Singleton
    fun provideCurrencyRatesService(): CurrencyRatesService {
        return CurrencyRatesServiceImpl()
    }
}
