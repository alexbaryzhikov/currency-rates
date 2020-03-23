package ru.alexb.currencyrates.rates.domain.model

import java.math.BigDecimal
import java.util.*

data class CurrencyRates(
    val baseCurrency: Currency,
    val rates: Map<Currency, BigDecimal>
) {

    fun multiplied(x: BigDecimal): CurrencyRates =
        copy(rates = rates.mapValues { it.value.multiply(x) })
}