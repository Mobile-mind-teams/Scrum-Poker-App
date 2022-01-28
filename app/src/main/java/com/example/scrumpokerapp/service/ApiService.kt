package com.example.scrumpokerapp.service

import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.service.response.BacklogResponse
import com.example.scrumpokerapp.service.response.SessionResponse
import com.example.scrumpokerapp.service.response.SessionsHistoryResponse
import com.example.scrumpokerapp.service.response.UsersResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/add")
    fun postUsers(@Body usersRegisterRequest: UsersRegisterRequest): Call<UsersResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") uid: String): Call<UsersResponse>

    @GET("session-history/users/all/{id}")
    fun getAllUserSessions(@Path("id") uid: String): Call<SessionsHistoryResponse>

    @GET("sessions/all")
    fun getAllSessions(): Call<SessionResponse>

    @GET("users/all")
    fun getAllUsers(): Call<UsersResponse>

    @GET("sessions/add")
    fun createSession(@Body session: Session): Call<SessionResponse>

    @GET("backlogs/all")
    fun getAllBacklogs(): Call<BacklogResponse>
}