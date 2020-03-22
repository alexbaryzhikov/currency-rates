package ru.alexb.currencyrates

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.domain.interactor.CurrencyRatesInteractor
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var currencyRatesInteractor: CurrencyRatesInteractor

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
            for (rates in currencyRatesInteractor.getRatesChannel()) {
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

    companion object {
        private const val TAG = "MainActivity"
    }
}
