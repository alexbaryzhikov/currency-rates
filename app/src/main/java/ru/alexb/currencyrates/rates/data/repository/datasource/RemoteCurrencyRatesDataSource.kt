package ru.alexb.currencyrates.rates.data.repository.datasource

import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import java.util.*

interface RemoteCurrencyRatesDataSource {
    suspend fun getRates(base: Currency): CurrencyRates
}
