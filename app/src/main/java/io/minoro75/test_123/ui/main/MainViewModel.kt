package io.minoro75.test_123.ui.main

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import io.minoro75.test_123.ui.data.entities.NbuResponse
import io.minoro75.test_123.ui.data.entities.P24Response
import io.minoro75.test_123.ui.repository.ExchangeRatesRepository
import io.minoro75.test_123.ui.utils.NetworkUtils
import io.minoro75.test_123.ui.utils.Resource
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel @ViewModelInject constructor(
    private val exchangeRatesRepository: ExchangeRatesRepository,
    networkUtils: NetworkUtils
) : ViewModel() {

    private val builder = MaterialDatePicker.Builder.datePicker()
    val picker = builder.build()
    private val _p24Rates = MutableLiveData<Resource<P24Response>>()
    val p24Rates : LiveData<Resource<P24Response>> = _p24Rates

    private val _nbuRates = MutableLiveData<Resource<NbuResponse>>()
    val nbuRates : LiveData<Resource<NbuResponse>> = _nbuRates


    private val _dateP24 = MutableLiveData<String>()
    val dateP24 : LiveData<String> = _dateP24

    private val _dateNbu = MutableLiveData<String>()
    val dateNbu : LiveData<String> = _dateP24

    init {
        builder.setTitleText("Select a date")
        //set current date
        val date = Calendar.getInstance().time
        val p24Format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) //p24 uses dd.mm.yyyy format in requests
        val p24Date = p24Format.format(date)
        _dateP24.value=p24Date

        val nbuFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault()) //nbu uses yyyymmdd
        val nbuDate = nbuFormat.format(date)
        _dateNbu.value = nbuDate

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

     fun datePickerShow(){
        picker.addOnPositiveButtonClickListener {
            val p24Format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) //p24 uses dd.mm.yyyy format in requests
            val p24Date = p24Format.format(picker.selection)
            Log.d("DatePicker ViewModel", "formatted date = $p24Date")
            _dateP24.value=p24Date

            val nbuFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault()) //nbu uses yyyymmdd
            val nbuDate = nbuFormat.format(picker.selection)
            Log.d("DatePicker ViewModel", "formatted date = $nbuDate")
            _dateNbu.value = nbuDate
            fetchRates()

        }

    }

     fun fetchRates() {
        viewModelScope.launch {
            try {
                _p24Rates.value = Resource.success(data = exchangeRatesRepository.getP24ExchangeRates(
                    _dateP24.value!!
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