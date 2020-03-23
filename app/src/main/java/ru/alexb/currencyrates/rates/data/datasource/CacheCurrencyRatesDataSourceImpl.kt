package ru.alexb.currencyrates.rates.data.datasource

import ru.alexb.currencyrates.rates.data.repository.datasource.CacheCurrencyRatesDataSource
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates

class CacheCurrencyRatesDataSourceImpl : CacheCurrencyRatesDataSource {
    override var rates: CurrencyRates? = null
}
