package io.minoro75.test_123.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.minoro75.test_123.ui.data.entities.NbuResponse
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

    private val _nbuRates = MutableLiveData<Resource<NbuResponse>>()
    val nbuRates : LiveData<Resource<NbuResponse>> = _nbuRates


    private val _date = MutableLiveData<String>()
    val date : LiveData<String> = _date

    private val _dateNbu = MutableLiveData<String>()
    val dateNbu : LiveData<String> = _date

    init {

        _date.value="10.10.2020"
        _dateNbu.value="20201010"

    _p24Rates.postValue(Resource.loading(null))
        if (networkUtils.isNetworkConnected()){
            //check if we connected to wifi\cellular and then fetch data
            fetchRates()
        }
        else{
            _p24Rates.postValue(Resource.error(null,"internet error"))
        }

    _nbuRates.postValue(Resource.loading(null))
        if (networkUtils.isNetworkConnected()){
            fetchRates()
        }

    }



    private fun fetchRates() {
        viewModelScope.launch {
            try {
                _p24Rates.value = Resource.success(data = exchangeRatesRepository.getP24ExchangeRates(
                    _date.value!!
                ))

                _nbuRates.value = Resource.success(data = exchangeRatesRepository.getNbuExchangeRates(
                    _dateNbu.value!!
                ))
            }

            catch (ex:Exception){
                _p24Rates.postValue(Resource.error(null, ex.localizedMessage?: "unexpected error"))
            }
        }
    }

}