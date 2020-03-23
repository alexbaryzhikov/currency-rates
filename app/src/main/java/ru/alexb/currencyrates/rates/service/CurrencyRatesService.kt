package ru.alexb.currencyrates.rates.service

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import java.math.BigDecimal
import java.util.*

interface CurrencyRatesService {
    val baseCurrencyChannel: SendChannel<Currency>
    val amountChannel: SendChannel<BigDecimal>
    val currencyRatesChannel: ReceiveChannel<CurrencyRates>

    fun start()
    fun stop()
}
