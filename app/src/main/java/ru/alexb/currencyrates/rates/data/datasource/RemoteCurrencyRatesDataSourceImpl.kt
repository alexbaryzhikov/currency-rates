package ru.alexb.currencyrates.rates.data.datasource

import ru.alexb.currencyrates.rates.data.datasource.api.CurrencyRatesApi
import ru.alexb.currencyrates.rates.data.dto.CurrencyRatesDto
import ru.alexb.currencyrates.rates.data.repository.datasource.RemoteCurrencyRatesDataSource
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import java.math.BigDecimal
import java.util.*

class RemoteCurrencyRatesDataSourceImpl(
    private val api: CurrencyRatesApi
) : RemoteCurrencyRatesDataSource {

    override suspend fun getRates(base: Currency): CurrencyRates =
        api.getCurrencyRatesAsync(base.currencyCode).toCurrencyRates()

    private fun CurrencyRatesDto.toCurrencyRates(): CurrencyRates = CurrencyRates(
        baseCurrency = Currency.getInstance(baseCurrency),
        rates = rates.entries.fold(hashMapOf()) { acc, (currency, rate) ->
            acc.apply { put(Currency.getInstance(currency), BigDecimal.valueOf(rate)) }
        }
    )
}
