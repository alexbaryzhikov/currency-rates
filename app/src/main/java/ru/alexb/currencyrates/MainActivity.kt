package ru.alexb.currencyrates

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.domain.interactor.CurrencyRatesInteractor
import ru.alexb.currencyrates.domain.model.CurrencyRates
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var currencyRatesInteractor: CurrencyRatesInteractor

    @Inject
    lateinit var ratesChannel: ReceiveChannel<CurrencyRates>

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var logJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        logJob = scope.launch {
            for (rates in ratesChannel) {
                Log.d(TAG, "rates = $rates")
            }
        }
        currencyRatesInteractor.startUpdates()
    }

    override fun onStop() {
        super.onStop()
        currencyRatesInteractor.stopUpdates()
        logJob?.cancel()
        logJob = null
    }

    fun onChangeBaseCurrencyClicked(view: View) {
        val currency = Random.Default.nextInt(currencies.size).let { currencies[it] }
        currencyRatesInteractor.setBaseCurrency(currency)
    }

    fun onChangeAmountClicked(view: View) {
        val amount = Random.Default.nextLong(10_000)
        currencyRatesInteractor.setAmount(BigDecimal.valueOf(amount))
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
