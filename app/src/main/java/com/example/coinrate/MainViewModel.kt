package com.example.coinrate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinrate.common.NetworkResponse
import com.example.coinrate.model.CoinCompany
import com.example.coinrate.use_case.GetCoinChartsUseCase
import com.example.coinrate.use_case.GetCoinMarketsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetCoinMarketsUseCase,
    private val chartUseCase: GetCoinChartsUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val message = MutableLiveData<String>()

    private val _coinValues = MutableLiveData<List<Float>>(emptyList())
    val coinValues: LiveData<List<Float>> get() = _coinValues
    private val _dateTimes = MutableLiveData<List<Float>>(emptyList())
    val dateTimes: LiveData<List<Float>> get() = _dateTimes

    private val _companies = MutableLiveData<List<CoinCompany>>(emptyList())
    val companies: LiveData<List<CoinCompany>> get() = _companies
    private val _selectedCompany = MutableLiveData<CoinCompany>()
    val selectedCompany: LiveData<CoinCompany> get() = _selectedCompany

    private val _searchDays = MutableLiveData<Int>(1)
    private var _selectedItem = MutableLiveData<Int>(-1)
    val selectedItem: LiveData<Int> get() = _selectedItem


    init {
        _coinValues.value = emptyList<Float>()
        _dateTimes.value = emptyList<Float>()
        getCoinCompanies()
    }

    private fun getCoinCompanies() {
        useCase().onEach { result ->
            when (result){
                is NetworkResponse.Success -> {
                    _companies.value = result.data ?: emptyList()
                    message.value = "success"
                }
                is NetworkResponse.Failure -> {
                    message.value = result.error ?: "error"
                }
                is NetworkResponse.Loading -> {
                    message.value = "loading..."
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectCompany(company: CoinCompany, position: Int) {
        _selectedItem.value = position
        _selectedCompany.value = company
        startSyncData()
    }

    fun setSearchDays(searchDays: Int) {
        _searchDays.value = searchDays
        startSyncData()
    }

   private fun getCoinCharts() {
        chartUseCase(selectedCompany.value!!, _searchDays.value!!).onEach { result ->
            when (result){
                is NetworkResponse.Success -> {
                    _dateTimes.value =  result.data!!.map { it.dateTime.toFloat() }
                    _coinValues.value =  result.data.map { it.price.toFloat() }
                }
                is NetworkResponse.Failure -> {
                    message.value = result.error ?: "error"
                }
                is NetworkResponse.Loading -> {
                    message.value = "loading..."
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun startSyncData() {
        compositeDisposable.clear()
        if(selectedCompany.value!!.id.isBlank()) return
        getCoinCharts()

        val disposable = Observable.interval(300, TimeUnit.SECONDS)
            .subscribeBy(
                onNext = {
                    if(selectedCompany.value!!.id.isNotBlank()) getCoinCharts()
                },
                onError = {}
            )
        compositeDisposable.add(disposable)
    }
}