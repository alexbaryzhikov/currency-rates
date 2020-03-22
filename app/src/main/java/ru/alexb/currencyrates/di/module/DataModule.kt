package ru.alexb.currencyrates.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexb.currencyrates.data.datasource.RemoteCurrencyRatesDataSourceImpl
import ru.alexb.currencyrates.data.datasource.api.CurrencyRatesApi
import ru.alexb.currencyrates.data.repository.CurrencyRatesRepositoryImpl
import ru.alexb.currencyrates.data.repository.datasource.RemoteCurrencyRatesDataSource
import ru.alexb.currencyrates.domain.repository.CurrencyRatesRepository

@Module
class DataModule {

    @Provides
    fun provideCurrencyRatesRepository(ds: RemoteCurrencyRatesDataSource): CurrencyRatesRepository {
        return CurrencyRatesRepositoryImpl(ds)
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

    companion object {
        private const val BASE_URL = "https://hiring.revolut.codes"
    }
}
