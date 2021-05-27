package com.cesar.school.avaliacaofinal.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class WeatherResponse {
    @SerializedName("list")
    var responseList: ArrayList<City> = ArrayList<City>()
}