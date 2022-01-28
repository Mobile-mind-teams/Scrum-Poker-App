package com.example.scrumpokerapp.service

import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.service.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/add")
    fun postUsers(@Body user: User): Call<UsersResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") uid: String): Call<UsersResponse>

    @GET("session-history/users/all/{id}")
    fun getAllUserSessions(@Path("id") uid: String): Call<SessionsHistoryResponse>

    @GET("sessions/all")
    fun getAllSessions(): Call<SessionResponse>

    @GET("sessions/all/{id}")
    fun getAllSessionsByPOID(@Path("id") po_id: String): Call<SessionResponse>

    @GET("users/all")
    fun getAllUsers(): Call<UsersResponse>

    @GET("sessions/add")
    fun createSession(@Body session: Session): Call<SessionResponse>

    @GET("backlogs/all")
    fun getAllBacklogs(): Call<BacklogResponse>

    @GET("stories/backlog/all/{id}")
    fun getAllStoriesFromBacklog(@Path("id") doc_id: String): Call<BacklogStoryResponse>
}