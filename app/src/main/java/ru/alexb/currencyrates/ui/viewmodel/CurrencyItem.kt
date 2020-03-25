package ru.alexb.currencyrates.ui.viewmodel

data class CurrencyItem(
    val code: String,
    val name: String,
    val iconResId: Int,
    val amount: String
)