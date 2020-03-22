package ru.alexb.currencyrates.data.datasource.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.alexb.currencyrates.data.dto.CurrencyRatesDto

interface CurrencyRatesApi {
    @GET("/api/android/latest")
    suspend fun getCurrencyRatesAsync(@Query("base") base: String): CurrencyRatesDto
}