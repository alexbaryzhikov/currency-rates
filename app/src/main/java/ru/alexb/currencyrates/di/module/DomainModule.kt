package ru.alexb.currencyrates.di.module

import dagger.Module
import dagger.Provides
import ru.alexb.currencyrates.domain.interactor.CurrencyRatesInteractor
import ru.alexb.currencyrates.domain.interactor.CurrencyRatesInteractorImpl
import ru.alexb.currencyrates.domain.repository.CurrencyRatesRepository

@Module
class DomainModule {

    @Provides
    fun provideCurrencyRatesInteractor(repo: CurrencyRatesRepository): CurrencyRatesInteractor {
        return CurrencyRatesInteractorImpl(repo)
    }
}