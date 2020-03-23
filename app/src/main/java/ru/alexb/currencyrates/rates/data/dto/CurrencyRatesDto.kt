package ru.alexb.currencyrates.rates.data.dto

data class CurrencyRatesDto(
    val baseCurrency: String,
    val rates: Map<String, Double>
)