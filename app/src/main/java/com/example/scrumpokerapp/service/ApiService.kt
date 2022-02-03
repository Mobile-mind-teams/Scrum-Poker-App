package com.example.scrumpokerapp.service

import com.example.scrumpokerapp.model.*
import com.example.scrumpokerapp.service.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("users/add")
    fun postUsers(@Body user: User): Call<UsersResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") uid: String): Call<UsersResponse>

    @GET("stories/session/all/{id}")
    fun getAllSessionStories(@Path("id") session_id: String): Call<SessionStoriesResponse>

    @GET("sessions/team/{email}")
    fun getAllUserSessionsByEmail(@Path("email") email: String): Call<SessionResponse>

    @GET("sessions/all")
    fun getAllSessions(): Call<SessionResponse>

    @GET("sessions/all/{id}")
    fun getAllSessionsByPOID(@Path("id") po_id: String): Call<SessionResponse>

    @GET("users/all")
    fun getAllUsers(): Call<UsersResponse>

    @POST("sessions/add")
    fun createSession(@Body session: Session): Call<SessionResponse>

    @GET("sessions/{id}&{status}")
    fun getSessionByAdminIDAndStatus(
        @Path("id") admin_uid: String,
        @Path("status") status: String
    ): Call<SessionResponse>

    @GET("backlogs/all")
    fun getAllBacklogs(): Call<BacklogResponse>

    @GET("stories/backlog/all/{id}")
    fun getAllStoriesFromBacklog(@Path("id") doc_id: String): Call<BacklogStoryResponse>

    @GET("users/all/available")
    fun getAllAvailableUsers(): Call<UsersResponse>

    @GET("projects/all/unassigned")
    fun getAllUnassignedProjects(): Call<ProjectResponse>

    @POST("email/send")
    fun sendEmail(@Body email: Email): Call<EmailResponse>

    @PATCH("projects/update/{id}")
    fun updateProject(@Body project: Project, @Path("id") project_id : String) : Call<ProjectResponse>

    @PATCH("users/update/{id}")
    fun updateUser(@Body user: User, @Path("id") doc_id : String) : Call<UsersResponse>

    @GET("cards/all/{type}")
    fun getDeckFor(@Path("type") role_string: String): Call<CardsResponse>

    @POST("table-cards/add/{document_id}")
    fun setTableCard(@Body userCard: UserCard, @Path("document_id") session_id: String): Call<TableCardResponse>

}