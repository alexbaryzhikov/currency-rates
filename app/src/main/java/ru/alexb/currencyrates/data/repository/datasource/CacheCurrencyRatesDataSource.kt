package ru.alexb.currencyrates.data.repository.datasource

import ru.alexb.currencyrates.domain.model.CurrencyRates

interface CacheCurrencyRatesDataSource {
    var rates: CurrencyRates?
}
