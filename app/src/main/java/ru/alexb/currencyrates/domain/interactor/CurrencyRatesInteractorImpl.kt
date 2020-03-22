package ru.alexb.currencyrates.domain.interactor

import android.util.Log
import kotlinx.coroutines.*
import ru.alexb.currencyrates.domain.model.CurrencyRates
import ru.alexb.currencyrates.domain.repository.CurrencyRatesRepository
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.TimeUnit

class CurrencyRatesInteractorImpl(
    private val currencyRatesRepository: CurrencyRatesRepository
) : CurrencyRatesInteractor {
    private var baseCurrency: Currency = BASE_CURRENCY_DEFAULT
    private var amount: BigDecimal = BigDecimal.ONE
    private var listener: CurrencyRatesListener = LISTENER_DEFAULT
    private var scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var updatesJob: Job? = null

    override fun setBaseCurrency(currency: Currency) {
        Log.v(TAG, "setBaseCurrency: currency = $currency")
        this.baseCurrency = currency
    }

    override fun setAmount(amount: BigDecimal) {
        Log.v(TAG, "setAmount: amount = $amount ")
        this.amount = amount
    }

    override fun setListener(listener: CurrencyRatesListener) {
        Log.v(TAG, "setListener: listener = $listener")
        this.listener = listener
    }

    override fun startUpdates() {
        Log.v(TAG, "startUpdates called")
        updatesJob = scope.launch {
            while (true) {
                val rates = currencyRatesRepository.getRates(baseCurrency)
                withContext(Dispatchers.Main) { listener.onUpdate(rates) }
                delay(UPDATE_INTERVAL)
            }
        }
    }

    override fun stopUpdates() {
        Log.v(TAG, "stopUpdates called")
        updatesJob?.cancel()
        updatesJob = null
    }

    companion object {
        private const val TAG = "CurrencyRatesInteractor"
        private val BASE_CURRENCY_DEFAULT = Currency.getInstance("EUR")
        private val LISTENER_DEFAULT = object : CurrencyRatesListener {
            override fun onUpdate(rates: CurrencyRates) {
                Log.d("DefaultListener", "rates = $rates")
            }
        }
        private val UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(1)
    }
}