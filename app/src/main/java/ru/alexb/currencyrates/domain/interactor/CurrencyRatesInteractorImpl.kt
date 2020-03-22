package ru.alexb.currencyrates.domain.interactor

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.domain.model.CurrencyRates
import ru.alexb.currencyrates.domain.repository.CurrencyRatesRepository
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.TimeUnit

class CurrencyRatesInteractorImpl(
    private val ratesRepository: CurrencyRatesRepository,
    private val ratesChannel: SendChannel<CurrencyRates>
) : CurrencyRatesInteractor {
    private var baseCurrency: Currency = BASE_CURRENCY_DEFAULT
    private var amount: BigDecimal = AMOUNT_DEFAULT
    private var scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var updatesJob: Job? = null

    override fun startUpdates() {
        Log.v(TAG, "startUpdates called")
        updatesJob = scope.launch {
            while (true) {
                updateRates()
                delay(UPDATE_INTERVAL)
            }
        }
    }

    private suspend fun updateRates() {
        val rates = ratesRepository.updateAndGetRates(baseCurrency)
        ratesChannel.send(rates.multiplied(amount))
    }

    override fun stopUpdates() {
        Log.v(TAG, "stopUpdates called")
        updatesJob?.cancel()
        updatesJob = null
    }

    override fun setBaseCurrency(currency: Currency) {
        Log.v(TAG, "setBaseCurrency: currency = $currency")
        this.baseCurrency = currency
        scope.launch { updateRates() }
    }

    override fun setAmount(amount: BigDecimal) {
        Log.v(TAG, "setAmount: amount = $amount ")
        this.amount = amount
        val rates = ratesRepository.getLastRates()
        ratesChannel.offer(rates.multiplied(amount))
    }

    companion object {
        private const val TAG = "CurrencyRatesInteractor"
        private val BASE_CURRENCY_DEFAULT = Currency.getInstance("EUR")
        private val AMOUNT_DEFAULT = BigDecimal.ONE
        private val UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(1)
    }
}