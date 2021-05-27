package com.cesar.school.avaliacaofinal.utils

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiInterface {

    @GET("/data/2.5/find")
    fun getSearchCityWeatherData(
        @Query("q") city: String?,
        @Query("units") metric: String?,
        @Query("lang") language: String?,
        @Query("appid") apiKey: String?
    ): Call<WeatherResponse>
}