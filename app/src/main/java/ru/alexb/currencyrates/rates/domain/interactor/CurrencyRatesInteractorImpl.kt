package ru.alexb.currencyrates.rates.domain.interactor

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import ru.alexb.currencyrates.rates.domain.repository.CurrencyRatesRepository
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class CurrencyRatesInteractorImpl(
    private val ratesRepository: CurrencyRatesRepository,
    private val baseCurrencyChannel: ReceiveChannel<Currency>,
    private val amountChannel: ReceiveChannel<BigDecimal>,
    private val ratesChannel: SendChannel<CurrencyRates>
) : CurrencyRatesInteractor {
    private var baseCurrency: Currency = BASE_CURRENCY_DEFAULT
    private var amount: BigDecimal = AMOUNT_DEFAULT
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val jobs: ArrayList<Job> = ArrayList()

    override fun onStart() {
        Log.v(TAG, "onStart called")
        jobs.add(scope.launch {
            while (true) {
                updateRates()
                delay(UPDATE_INTERVAL)
            }
        })
        jobs.add(scope.launch {
            for (currency in baseCurrencyChannel) {
                setBaseCurrency(currency)
            }
        })
        jobs.add(scope.launch {
            for (amount in amountChannel) {
                setAmount(amount)
            }
        })
    }

    private suspend fun updateRates() {
        val rates = ratesRepository.updateAndGetRates(baseCurrency)
        ratesChannel.send(rates.copy(amount = amount))
    }

    private fun setBaseCurrency(currency: Currency) {
        Log.v(TAG, "setBaseCurrency: currency = $currency")
        this.baseCurrency = currency
        val rates = ratesRepository.getLastRates().rates
        this.amount = rates.getValue(currency).multiply(this.amount)
        scope.launch { updateRates() }
    }

    private fun setAmount(amount: BigDecimal) {
        Log.v(TAG, "setAmount: amount = $amount ")
        this.amount = amount
        val rates = ratesRepository.getLastRates()
        ratesChannel.offer(rates.copy(amount = amount))
    }

    override fun onStop() {
        Log.v(TAG, "onStop called")
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    companion object {
        private const val TAG = "CurrencyRatesInteractor"
        private val BASE_CURRENCY_DEFAULT = Currency.getInstance("EUR")
        private val AMOUNT_DEFAULT = BigDecimal.ONE
        private val UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(1)
    }
}
