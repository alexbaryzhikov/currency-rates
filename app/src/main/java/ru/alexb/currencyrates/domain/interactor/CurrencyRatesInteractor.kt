package ru.alexb.currencyrates.domain.interactor

import kotlinx.coroutines.channels.ReceiveChannel
import ru.alexb.currencyrates.domain.model.CurrencyRates
import java.math.BigDecimal
import java.util.*

interface CurrencyRatesInteractor {
    fun getRatesChannel(): ReceiveChannel<CurrencyRates>
    fun startUpdates()
    fun stopUpdates()
    fun setBaseCurrency(currency: Currency)
    fun setAmount(amount: BigDecimal)
}