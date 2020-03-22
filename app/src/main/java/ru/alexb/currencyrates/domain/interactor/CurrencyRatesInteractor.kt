package ru.alexb.currencyrates.domain.interactor

import java.math.BigDecimal
import java.util.*

interface CurrencyRatesInteractor {
    fun setBaseCurrency(currency: Currency)
    fun setAmount(amount: BigDecimal)
    fun setListener(listener: CurrencyRatesListener)
    fun startUpdates()
    fun stopUpdates()
}