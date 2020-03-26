package ru.alexb.currencyrates.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.alexb.currencyrates.R
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import ru.alexb.currencyrates.ui.view.adapter.CurrencyRecyclerViewAdapter
import ru.alexb.currencyrates.ui.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: CurrencyRecyclerViewAdapter

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var currencyRatesService: CurrencyRatesService

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.initUiComponent(this)
        Injector.uiComponent().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ratesView.setHasFixedSize(true)
        ratesView.layoutManager = layoutManager
        ratesView.adapter = adapter

        viewModel.getCurrencyItems().observe(this, Observer { adapter.updateItems(it) })
    }

    override fun onStart() {
        super.onStart()
        currencyRatesService.start()
    }

    override fun onStop() {
        super.onStop()
        currencyRatesService.stop()
    }

    override fun onDestroy() {
        Injector.invalidateUiComponent()
        super.onDestroy()
    }
}
