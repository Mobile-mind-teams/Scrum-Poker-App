package com.example.scrumpokerapp.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://192.168.3.7:5000/scrum-poker/v1/"

class ApiClient {
    fun initRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(ApiService::class.java)
}