package ru.alexb.currencyrates.ui.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.alexb.currencyrates.R
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import ru.alexb.currencyrates.ui.controller.MainViewController
import ru.alexb.currencyrates.ui.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var currencyRatesService: CurrencyRatesService

    @Inject
    lateinit var viewController: MainViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.mainComponent.uiComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        val adapter = CurrencyRecyclerViewAdapter(layoutManager, viewController)
        ratesView.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            this.adapter = adapter
        }

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getCurrencyItems().observe(this, Observer {
            Log.d(TAG, "items = $it")
            adapter.updateItems(it)
        })
    }

    override fun onStart() {
        super.onStart()
        currencyRatesService.start()
    }

    override fun onStop() {
        super.onStop()
        currencyRatesService.stop()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
