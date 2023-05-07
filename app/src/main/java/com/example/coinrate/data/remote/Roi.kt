package com.example.coinrate.data.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Roi(
    val currency: String?,
    val percentage: Double?,
    val times: Double?
)