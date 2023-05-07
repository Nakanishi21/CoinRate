package com.example.coinrate.model

data class CoinCompany(
    val id: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val priceChangePercentage: Double
)