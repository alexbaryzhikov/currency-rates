package ru.alexb.currencyrates.rates.domain.repository

import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import java.util.*

interface CurrencyRatesRepository {
    suspend fun updateAndGetRates(base: Currency): CurrencyRates
    fun getLastRates(): CurrencyRates
}