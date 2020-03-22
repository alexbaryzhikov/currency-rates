package ru.alexb.currencyrates.data.datasource

import ru.alexb.currencyrates.data.repository.datasource.CacheCurrencyRatesDataSource
import ru.alexb.currencyrates.domain.model.CurrencyRates

class CacheCurrencyRatesDataSourceImpl : CacheCurrencyRatesDataSource {
    override var rates: CurrencyRates? = null
}
