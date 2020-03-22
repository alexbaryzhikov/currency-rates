package ru.alexb.currencyrates.domain.interactor

import java.math.BigDecimal
import java.util.*

interface CurrencyRatesInteractor {
    fun startUpdates()
    fun stopUpdates()
    fun setBaseCurrency(currency: Currency)
    fun setAmount(amount: BigDecimal)
}