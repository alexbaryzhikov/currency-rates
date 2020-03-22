package ru.alexb.currencyrates.data.repository.datasource

import ru.alexb.currencyrates.domain.model.CurrencyRates
import java.util.*

interface RemoteCurrencyRatesDataSource {
    suspend fun getRates(base: Currency): CurrencyRates
}
