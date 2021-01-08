package io.minoro75.test_123.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.minoro75.test_123.ui.data.entities.P24Response
import io.minoro75.test_123.ui.repository.ExchangeRatesRepository
import io.minoro75.test_123.ui.utils.NetworkUtils
import io.minoro75.test_123.ui.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    networkUtils: NetworkUtils
) : ViewModel() {

    private val _p24Rates = MutableLiveData<Resource<P24Response>>()
    val p24Rates : LiveData<Resource<P24Response>> = _p24Rates

    private val _dd = MutableLiveData<Int>()
    val dd : LiveData<Int> = _dd

    private val _mm = MutableLiveData<Int>()
    val mm : LiveData<Int> = _mm

    private val _yyyy = MutableLiveData<Int>()
    val yyyy : LiveData<Int> = _yyyy

    private val _date = MutableLiveData<String>()
    val date : LiveData<String> = _date

    init {
        _dd.value = 10
        _mm.value = 12
        _yyyy.value = 2020

        _date.value="10.10.2020"

    _p24Rates.postValue(Resource.loading(null))
        if (networkUtils.isNetworkConnected()){
            //check if we connected to wifi\cellular and then fetch data
            fetchRates()
        }
        else{
            _p24Rates.postValue(Resource.error(null,"internet error"))
        }
    }

    private fun fetchRates() {
        viewModelScope.launch {
            try {
                _p24Rates.value = Resource.success(data = exchangeRatesRepository.getP24ExchangeRates(
                    _date.value!!
                ))
            }

            catch (ex:Exception){
                _p24Rates.postValue(Resource.error(null, ex.localizedMessage?: "unexpected error"))
            }
        }
    }

}