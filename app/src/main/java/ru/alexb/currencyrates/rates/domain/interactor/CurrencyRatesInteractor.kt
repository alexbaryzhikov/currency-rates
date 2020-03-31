package ru.alexb.currencyrates.rates.domain.interactor

interface CurrencyRatesInteractor {
    fun observeRates()
    fun stopObservingRates()
}
