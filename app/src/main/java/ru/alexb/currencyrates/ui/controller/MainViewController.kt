package ru.alexb.currencyrates.ui.controller

interface MainViewController {
    fun setBaseCurrency(currencyCode: String)
    fun setAmount(amount: String)
}