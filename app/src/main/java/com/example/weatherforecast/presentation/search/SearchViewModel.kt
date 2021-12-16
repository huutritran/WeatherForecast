package com.example.weatherforecast.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.core.Failure
import com.example.weatherforecast.domain.models.WeatherInfo
import com.example.weatherforecast.domain.usecases.SearchWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import androidx.databinding.ObservableField
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val searchWeather: SearchWeather) : ViewModel() {
    private val _weatherData = MutableLiveData<List<WeatherInfo>>()
    val weatherData: LiveData<List<WeatherInfo>> = _weatherData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _failure = MutableLiveData<Failure>()
    val failure: LiveData<Failure> = _failure

    var searchKey = ObservableField<String>()

    fun search() = viewModelScope.launch {
        if (searchKey.get().isNullOrEmpty()) {
            return@launch
        }

        val searchValue =  searchKey.get()?.lowercase().orEmpty()

        _loading.value = true

        searchWeather(SearchWeather.Params(searchValue))
            .fold(
                ifLeft = this@SearchViewModel::onFailure,
                ifRight = this@SearchViewModel::onSuccess
            ).also {
                _loading.value = false
            }
    }

    private fun onSuccess(data: List<WeatherInfo>) {
        _weatherData.value = data
    }

    private fun onFailure(failure: Failure) {
        _failure.value = failure
    }
}