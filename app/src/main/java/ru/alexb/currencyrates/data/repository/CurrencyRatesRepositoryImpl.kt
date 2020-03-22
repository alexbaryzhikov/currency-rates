package ru.alexb.currencyrates.data.repository

import ru.alexb.currencyrates.data.repository.datasource.RemoteCurrencyRatesDataSource
import ru.alexb.currencyrates.domain.model.CurrencyRates
import ru.alexb.currencyrates.domain.repository.CurrencyRatesRepository
import java.util.*

class CurrencyRatesRepositoryImpl(
    private val ds: RemoteCurrencyRatesDataSource
): CurrencyRatesRepository {

    override suspend fun getRates(base: Currency): CurrencyRates = ds.getRates(base)
}