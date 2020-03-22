package ru.alexb.currencyrates.data.dto

data class CurrencyRatesDto(
    val baseCurrency: String,
    val rates: Map<String, Double>
)