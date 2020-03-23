package ru.alexb.currencyrates.rates.data.repository.datasource

import ru.alexb.currencyrates.rates.domain.model.CurrencyRates

interface CacheCurrencyRatesDataSource {
    var rates: CurrencyRates?
}
