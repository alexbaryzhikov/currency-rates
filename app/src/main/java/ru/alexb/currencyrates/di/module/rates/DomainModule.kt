package ru.alexb.currencyrates.di.module.rates

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.di.annotation.scope.RatesScope
import ru.alexb.currencyrates.rates.domain.interactor.CurrencyRatesInteractor
import ru.alexb.currencyrates.rates.domain.interactor.CurrencyRatesInteractorImpl
import ru.alexb.currencyrates.rates.domain.model.CurrencyRates
import ru.alexb.currencyrates.rates.domain.repository.CurrencyRatesRepository
import java.math.BigDecimal
import java.util.*

@Module
class DomainModule {

    @Provides
    @RatesScope
    fun provideCurrencyRatesInteractor(
        ratesRepository: CurrencyRatesRepository,
        baseCurrencyChannel: ReceiveChannel<@JvmSuppressWildcards Currency>,
        amountChannel: ReceiveChannel<@JvmSuppressWildcards BigDecimal>,
        ratesChannel: SendChannel<@JvmSuppressWildcards CurrencyRates>
    ): CurrencyRatesInteractor {
        return CurrencyRatesInteractorImpl(
            ratesRepository,
            baseCurrencyChannel,
            amountChannel,
            ratesChannel
        )
    }

    @Provides
    @RatesScope
    fun provideRatesChannel(): Channel<CurrencyRates> = Channel(Channel.CONFLATED)

    @Provides
    fun provideSendRatesChannel(
        channel: Channel<CurrencyRates>
    ): SendChannel<CurrencyRates> = channel

    @Provides
    fun provideReceiveRatesChannel(
        channel: Channel<CurrencyRates>
    ): ReceiveChannel<CurrencyRates> = channel

    @Provides
    @RatesScope
    fun provideBaseCurrencyChannel(): Channel<Currency> = Channel(Channel.CONFLATED)

    @Provides
    fun provideSendBaseCurrencyChannel(
        channel: Channel<Currency>
    ): SendChannel<Currency> = channel

    @Provides
    fun provideReceiveBaseCurrencyChannel(
        channel: Channel<Currency>
    ): ReceiveChannel<Currency> = channel

    @Provides
    @RatesScope
    fun provideAmountChannel(): Channel<BigDecimal> = Channel(Channel.CONFLATED)

    @Provides
    fun provideSendAmountChannel(
        channel: Channel<BigDecimal>
    ): SendChannel<BigDecimal> = channel

    @Provides
    fun provideReceiveAmountChannel(
        channel: Channel<BigDecimal>
    ): ReceiveChannel<BigDecimal> = channel
}
