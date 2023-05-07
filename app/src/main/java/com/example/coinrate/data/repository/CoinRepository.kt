package com.example.coinrate.data.repository

import com.example.coinrate.data.remote.CoinChartsResultDto
import com.example.coinrate.data.remote.CoinGeckoApi
import com.example.coinrate.data.remote.CoinMarketsResultDtoItem
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val api: CoinGeckoApi
){
    suspend fun getCoinMarkets(): List<CoinMarketsResultDtoItem> {
        return api.getCoinMarkets(
            vs_currency= "jpy",
            order= "market_cap_desc",
            per_page = 10,
            page = 1,
            sparkline = false,
            locale = "en"
        )
    }

    suspend fun getCoinCharts(id: String, days: Int): CoinChartsResultDto {
        return api.getCoinCharts(
            id = id,
            vs_currency= "jpy",
            days = days.toString()
        )
    }
}