package com.cesar.school.avaliacaofinal.utils

class ApiClient {
    val BASE_URL = "http://api.openweathermap.org"

    fun getApiInterface(): ApiInterface? {
        return RetrofitInstance().getClient(BASE_URL)?.create(ApiInterface::class.java)
    }
}