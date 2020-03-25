package ru.alexb.currencyrates.ui.controller

import android.util.Log
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import java.math.BigDecimal
import java.util.*

class MainViewControllerImpl(
    private val service: CurrencyRatesService
) : MainViewController {

    override fun setBaseCurrency(currencyCode: String) {
        Log.v(TAG, "setBaseCurrency: currencyCode = $currencyCode")
        val currency = Currency.getInstance(currencyCode)
        service.baseCurrencyChannel.offer(currency)
    }

    override fun setAmount(amount: String) {
        Log.v(TAG, "setAmount: amount = $amount")
        val amountNum = BigDecimal(amount)
        service.amountChannel.offer(amountNum)
    }

    companion object {
        private const val TAG = "MainViewControllerImpl"
    }
}