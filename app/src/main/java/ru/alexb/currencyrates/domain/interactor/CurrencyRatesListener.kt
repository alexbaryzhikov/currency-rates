package ru.alexb.currencyrates.domain.interactor

import ru.alexb.currencyrates.domain.model.CurrencyRates

interface CurrencyRatesListener {
    fun onUpdate(rates: CurrencyRates)
}