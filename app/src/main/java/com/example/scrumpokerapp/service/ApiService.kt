package com.example.scrumpokerapp.service

import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.service.response.AddUserResponse
import com.example.scrumpokerapp.service.response.GetUsersResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/add")
    fun postUsers(@Body usersRegisterRequest: UsersRegisterRequest): Call<AddUserResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") uid: String): Call<GetUsersResponse>
}