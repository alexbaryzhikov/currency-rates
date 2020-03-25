package ru.alexb.currencyrates.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.alexb.currencyrates.R
import ru.alexb.currencyrates.di.Injector
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject

class MainViewModel : ViewModel() {
    @Inject
    lateinit var currencyRatesService: CurrencyRatesService

    private val currencyItems = MutableLiveData<List<CurrencyItem>>()
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val updateJob: Job

    init {
        Injector.mainComponent.uiComponent().inject(this)
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
            iconResId = R.drawable.flag_australia,
            amount = amount.round3().toPlainString()
        )
        val items = rates.map { (currency, rate) ->
            CurrencyItem(
                code = currency.currencyCode,
                name = currency.displayName,
                iconResId = R.drawable.flag_australia,
                amount = rate.multiply(amount).round3().toPlainString()
            )
        }
        return listOf(baseItem) + items.sortedBy { it.code }
    }

    private fun BigDecimal.round3() : BigDecimal =
        round(MathContext(precision() - scale() + 3, RoundingMode.HALF_UP))

    fun getCurrencyItems(): LiveData<List<CurrencyItem>> = currencyItems

    override fun onCleared() {
        updateJob.cancel()
    }
}
