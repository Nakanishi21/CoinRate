package com.example.coinrate.use_case

import com.example.coinrate.common.NetworkResponse
import com.example.coinrate.data.remote.toCoinChars
import com.example.coinrate.data.repository.CoinRepository
import com.example.coinrate.model.CoinChart
import com.example.coinrate.model.CoinCompany
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinChartsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(
        coinCompany: CoinCompany,
        days: Int
    ): Flow<NetworkResponse<List<CoinChart>>> = flow {
        try {
            emit(NetworkResponse.Loading<List<CoinChart>>())
            val coinCompanies = repository.getCoinCharts(coinCompany.id, days).toCoinChars()
            emit(NetworkResponse.Success<List<CoinChart>>(coinCompanies))
        } catch (e: Exception) {
            emit(NetworkResponse.Failure<List<CoinChart>>(e.message.toString()))
        }
    }
}