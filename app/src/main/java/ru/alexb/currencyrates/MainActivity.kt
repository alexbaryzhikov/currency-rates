package ru.alexb.currencyrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.domain.interactor.CurrencyRatesInteractor
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var currencyRatesInteractor: CurrencyRatesInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.mainComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        currencyRatesInteractor.startUpdates()
    }

    override fun onStop() {
        super.onStop()
        currencyRatesInteractor.stopUpdates()
    }
}
