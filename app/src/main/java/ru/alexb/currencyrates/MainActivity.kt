package ru.alexb.currencyrates

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import java.math.BigDecimal
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val service: CurrencyRatesService by lazy {
        Injector.mainComponent.rates().currencyRatesService()
    }
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var logJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        service.start()
        logJob = scope.launch {
            for (rates in service.currencyRatesChannel) {
                Log.d(TAG, "rates = $rates")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        service.stop()
        logJob?.cancel()
        logJob = null
    }

    fun onChangeBaseCurrencyClicked(view: View) {
        val currency = Random.Default.nextInt(currencies.size).let { currencies[it] }
        service.baseCurrencyChannel.offer(currency)
    }

    fun onChangeAmountClicked(view: View) {
        val amount = Random.Default.nextLong(10_000)
        service.amountChannel.offer(BigDecimal.valueOf(amount))
    }

    companion object {
        private const val TAG = "MainActivity"
        private val currencies = listOf(
            Currency.getInstance("AUD"),
            Currency.getInstance("BGN"),
            Currency.getInstance("BRL"),
            Currency.getInstance("CAD"),
            Currency.getInstance("CHF"),
            Currency.getInstance("CNY"),
            Currency.getInstance("CZK"),
            Currency.getInstance("DKK"),
            Currency.getInstance("EUR"),
            Currency.getInstance("GBP"),
            Currency.getInstance("HKD"),
            Currency.getInstance("HRK"),
            Currency.getInstance("HUF"),
            Currency.getInstance("IDR"),
            Currency.getInstance("ILS"),
            Currency.getInstance("INR"),
            Currency.getInstance("ISK"),
            Currency.getInstance("JPY"),
            Currency.getInstance("KRW"),
            Currency.getInstance("MXN"),
            Currency.getInstance("MYR"),
            Currency.getInstance("NOK"),
            Currency.getInstance("NZD"),
            Currency.getInstance("PHP"),
            Currency.getInstance("PLN"),
            Currency.getInstance("RON"),
            Currency.getInstance("RUB"),
            Currency.getInstance("SEK"),
            Currency.getInstance("SGD"),
            Currency.getInstance("THB"),
            Currency.getInstance("USD"),
            Currency.getInstance("ZAR")
        )
    }
}
