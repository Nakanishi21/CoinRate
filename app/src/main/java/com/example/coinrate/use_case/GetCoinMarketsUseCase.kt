package com.example.coinrate.use_case

import com.example.coinrate.common.NetworkResponse
import com.example.coinrate.data.remote.toCoinCompany
import com.example.coinrate.data.repository.CoinRepository
import com.example.coinrate.model.CoinCompany
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinMarketsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<NetworkResponse<List<CoinCompany>>> = flow {
        try {
            emit(NetworkResponse.Loading<List<CoinCompany>>())
            val coinCompanies = repository.getCoinMarkets().map{ it.toCoinCompany() }
            emit(NetworkResponse.Success<List<CoinCompany>>(coinCompanies))
        } catch (e: Exception) {
            emit(NetworkResponse.Failure<List<CoinCompany>>(e.message.toString()))
        }
    }
}