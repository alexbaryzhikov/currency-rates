package ru.alexb.currencyrates.rates.data.repository

import ru.alexb.currencyrates.rates.data.repository.datasource.CacheCurrencyRatesDataSource
import ru.alexb.currencyrates.rates.data.repository.datasource.RemoteCurrencyRatesDataSource
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import ru.alexb.currencyrates.rates.domain.repository.CurrencyRatesRepository
import java.util.*
import kotlin.NoSuchElementException

class CurrencyRatesRepositoryImpl(
    private val remoteDs: RemoteCurrencyRatesDataSource,
    private val cacheDs: CacheCurrencyRatesDataSource
): CurrencyRatesRepository {

    override suspend fun updateAndGetRates(base: Currency): CurrencyRates =
        remoteDs.getRates(base).also { cacheDs.rates = it }

    override fun getLastRates(): CurrencyRates =
        cacheDs.rates ?: throw NoSuchElementException("Cache is empty")
}