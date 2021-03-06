package ru.alexb.currencyrates.di.module.rates

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexb.currencyrates.di.annotation.scope.RatesScope
import ru.alexb.currencyrates.rates.data.datasource.CacheCurrencyRatesDataSourceImpl
import ru.alexb.currencyrates.rates.data.datasource.RemoteCurrencyRatesDataSourceImpl
import ru.alexb.currencyrates.rates.data.datasource.api.CurrencyRatesApi
import ru.alexb.currencyrates.rates.data.repository.CurrencyRatesRepositoryImpl
import ru.alexb.currencyrates.rates.data.repository.datasource.CacheCurrencyRatesDataSource
import ru.alexb.currencyrates.rates.data.repository.datasource.RemoteCurrencyRatesDataSource
import ru.alexb.currencyrates.rates.domain.repository.CurrencyRatesRepository

@Module
class DataModule {

    @Provides
    @RatesScope
    fun provideCurrencyRatesRepository(
        remoteDs: RemoteCurrencyRatesDataSource,
        cacheDs: CacheCurrencyRatesDataSource
    ): CurrencyRatesRepository {
        return CurrencyRatesRepositoryImpl(remoteDs, cacheDs)
    }

    @Provides
    fun provideRemoteCurrencyRatesDataSource(api: CurrencyRatesApi): RemoteCurrencyRatesDataSource {
        return RemoteCurrencyRatesDataSourceImpl(api)
    }

    @Provides
    fun provideCurrencyRatesApi(retrofit: Retrofit): CurrencyRatesApi {
        return retrofit.create(CurrencyRatesApi::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCacheCurrencyRatesDataSource(): CacheCurrencyRatesDataSource {
        return CacheCurrencyRatesDataSourceImpl()
    }

    companion object {
        private const val BASE_URL = "https://hiring.revolut.codes"
    }
}
