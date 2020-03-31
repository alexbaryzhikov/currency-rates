package ru.alexb.currencyrates.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class MainViewModel : ViewModel() {
    @Inject
    lateinit var currencyRatesService: CurrencyRatesService

    private val currencyItems = MutableLiveData<List<CurrencyItem>>()
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val updateJob: Job

    init {
        Injector.uiComponent().inject(this)
        updateJob = scope.launch {
            for (rates in currencyRatesService.currencyRatesChannel) {
                currencyItems.value = rates.toCurrencyItems()
            }
        }
    }

    private fun CurrencyRates.toCurrencyItems(): List<CurrencyItem> {
        val baseItem = CurrencyItem(
            code = baseCurrency.currencyCode,
            name = baseCurrency.displayName,
            iconResId = currencyIcons.getValue(baseCurrency.currencyCode),
            amount = amount.round3().toPlainString()
        )
        val items = rates.map { (currency, rate) ->
            CurrencyItem(
                code = currency.currencyCode,
                name = currency.displayName,
                iconResId = currencyIcons.getValue(currency.currencyCode),
                amount = rate.multiply(amount).round3().toPlainString()
            )
        }
        return listOf(baseItem) + items.sortedBy { it.code }
    }

    private fun BigDecimal.round3(): BigDecimal = setScale(3, RoundingMode.HALF_EVEN)

    fun getCurrencyItems(): LiveData<List<CurrencyItem>> = currencyItems

    fun onActivityStart() {
        currencyRatesService.start()
    }

    fun onActivityStop() {
        currencyRatesService.stop()
    }

    override fun onCleared() {
        updateJob.cancel()
    }
}
