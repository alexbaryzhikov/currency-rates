package ru.alexb.currencyrates.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import ru.alexb.currencyrates.domain.interactor.CurrencyRatesInteractor
import ru.alexb.currencyrates.domain.interactor.CurrencyRatesInteractorImpl
import ru.alexb.currencyrates.domain.model.CurrencyRates
import ru.alexb.currencyrates.domain.repository.CurrencyRatesRepository
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    fun provideCurrencyRatesInteractor(
        ratesRepository: CurrencyRatesRepository,
        ratesChannel: SendChannel<@JvmSuppressWildcards CurrencyRates>
    ): CurrencyRatesInteractor {
        return CurrencyRatesInteractorImpl(ratesRepository, ratesChannel)
    }

    @Provides
    fun provideSendRatesChannel(
        ratesChannel: Channel<CurrencyRates>
    ): SendChannel<CurrencyRates> {
        return ratesChannel
    }

    @Provides
    fun provideReceiveRatesChannel(
        ratesChannel: Channel<CurrencyRates>
    ): ReceiveChannel<CurrencyRates> {
        return ratesChannel
    }

    @Provides
    @Singleton
    fun provideRatesChannel(): Channel<CurrencyRates> {
        return Channel(CONFLATED)
    }
}