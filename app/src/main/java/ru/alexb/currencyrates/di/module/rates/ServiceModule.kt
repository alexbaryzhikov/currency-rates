package ru.alexb.currencyrates.di.module.rates

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.di.annotation.scope.RatesScope
import ru.alexb.currencyrates.rates.domain.interactor.CurrencyRatesInteractor
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import ru.alexb.currencyrates.rates.service.CurrencyRatesService
import ru.alexb.currencyrates.rates.service.CurrencyRatesServiceImpl
import java.math.BigDecimal
import java.util.*

@Module
class ServiceModule {

    @Provides
    @RatesScope
    fun provideCurrencyRatesService(
        currencyRatesInteractor: CurrencyRatesInteractor,
        baseCurrencyChannel: SendChannel<@JvmSuppressWildcards Currency>,
        amountChannel: SendChannel<@JvmSuppressWildcards BigDecimal>,
        currencyRatesChannel: ReceiveChannel<@JvmSuppressWildcards CurrencyRates>
    ): CurrencyRatesService {
        return CurrencyRatesServiceImpl(
            currencyRatesInteractor,
            baseCurrencyChannel,
            amountChannel,
            currencyRatesChannel
        )
    }
}