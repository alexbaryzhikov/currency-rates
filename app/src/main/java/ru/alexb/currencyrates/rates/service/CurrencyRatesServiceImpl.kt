package ru.alexb.currencyrates.rates.service

import android.util.Log
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.rates.domain.interactor.CurrencyRatesInteractor
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class CurrencyRatesServiceImpl : CurrencyRatesService {
    @Inject
    lateinit var currencyRatesInteractor: CurrencyRatesInteractor

    @Inject
    override lateinit var baseCurrencyChannel: SendChannel<@JvmSuppressWildcards Currency>

    @Inject
    override lateinit var amountChannel: SendChannel<@JvmSuppressWildcards BigDecimal>

    @Inject
    override lateinit var currencyRatesChannel: ReceiveChannel<@JvmSuppressWildcards CurrencyRates>

    init {
        Injector.mainComponent().ratesComponent().inject(this)
    }

    override fun start() {
        Log.v(TAG, "start called")
        currencyRatesInteractor.observeRates()
    }

    override fun stop() {
        Log.v(TAG, "stop called")
        currencyRatesInteractor.stopObservingRates()
    }

    companion object {
        private const val TAG = "CurrencyRatesServiceImpl"
    }
}
