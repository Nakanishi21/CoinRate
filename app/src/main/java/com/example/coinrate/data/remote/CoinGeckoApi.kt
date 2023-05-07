package com.example.coinrate.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") vs_currency: String,
        @Query("order") order: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
        @Query("sparkline") sparkline: Boolean,
        @Query("locale") locale: String,
    ): List<CoinMarketsResultDtoItem>

    @GET("coins/{id}/market_chart")
    suspend fun getCoinCharts(
        @Path("id") id: String,
        @Query("vs_currency") vs_currency: String,
        @Query("days") days: String
    ): CoinChartsResultDto
}