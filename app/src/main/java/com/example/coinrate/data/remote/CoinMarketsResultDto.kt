package com.example.coinrate.data.remote


import com.example.coinrate.model.CoinCompany
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class CoinMarketsResultDto : ArrayList<CoinMarketsResultDtoItem>(

)

fun CoinMarketsResultDto.toCoinCompanies(): List<CoinCompany> {
    return this.map{
        it.toCoinCompany()
    }
}