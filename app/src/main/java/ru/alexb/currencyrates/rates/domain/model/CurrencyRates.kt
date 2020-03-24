package ru.alexb.currencyrates.rates.domain.model

import java.math.BigDecimal
import java.util.*

data class CurrencyRates(
    val baseCurrency: Currency,
    val rates: Map<Currency, BigDecimal>,
    val amount: BigDecimal = BigDecimal.ONE
)
