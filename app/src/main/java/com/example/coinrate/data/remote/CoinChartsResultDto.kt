package com.example.coinrate.data.remote


import android.util.Log
import com.example.coinrate.model.CoinChart
import com.example.coinrate.model.CoinCompany
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinChartsResultDto(
    @Json(name = "market_caps")
    val marketCaps: List<List<Double?>?>?,
    val prices: List<List<Double>>?,
    @Json(name = "total_volumes")
    val totalVolumes: List<List<Double?>?>?
)

fun CoinChartsResultDto.toCoinChars(): List<CoinChart> {
    return prices!!.map {
        CoinChart(
            it[0],
            it[1]
        )
    }
}