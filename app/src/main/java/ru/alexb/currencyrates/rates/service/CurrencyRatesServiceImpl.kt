package ru.alexb.currencyrates.rates.service

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.rates.domain.interactor.CurrencyRatesInteractor
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import java.math.BigDecimal
import java.util.*

class CurrencyRatesServiceImpl(
    private val currencyRatesInteractor: CurrencyRatesInteractor,
    override val baseCurrencyChannel: SendChannel<Currency>,
    override val amountChannel: SendChannel<BigDecimal>,
    override val currencyRatesChannel: ReceiveChannel<CurrencyRates>
) : CurrencyRatesService {

    override fun start() {
        currencyRatesInteractor.onStart()
    }

    override fun stop() {
        currencyRatesInteractor.onStop()
    }
}
