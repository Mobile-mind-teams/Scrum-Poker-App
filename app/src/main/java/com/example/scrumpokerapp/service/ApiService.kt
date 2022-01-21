package com.example.scrumpokerapp.service

import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.service.response.AddUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/add")
    fun postUsers(@Body usersRegisterRequest: UsersRegisterRequest): Call<AddUserResponse>
}