package ru.alexb.currencyrates.data.datasource

import ru.alexb.currencyrates.data.datasource.api.CurrencyRatesApi
import ru.alexb.currencyrates.data.dto.CurrencyRatesDto
import ru.alexb.currencyrates.data.repository.datasource.RemoteCurrencyRatesDataSource
import ru.alexb.currencyrates.domain.model.CurrencyRates
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
