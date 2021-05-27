package com.cesar.school.avaliacaofinal.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesar.school.avaliacaofinal.utils.City
import com.cesar.school.avaliacaofinal.utils.WeatherResponse

class SearchViewModel : ViewModel() {
    var weatherList = MutableLiveData<ArrayList<City>>()
    var tempList = ArrayList<City>()

    internal fun getFileList(): MutableLiveData<List<City>> {
        if (weatherList == null) {
            weatherList = MutableLiveData()
        }
        return weatherList as MutableLiveData<List<City>>
    }

    internal fun updateCities(cities: ArrayList<City>){
        tempList.clear()
        tempList = cities
        weatherList.value = tempList
    }
}