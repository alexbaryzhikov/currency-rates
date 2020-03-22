package ru.alexb.currencyrates.domain.repository

import ru.alexb.currencyrates.domain.model.CurrencyRates
import java.util.*

interface CurrencyRatesRepository {
    suspend fun updateAndGetRates(base: Currency): CurrencyRates
    fun getLastRates(): CurrencyRates
}