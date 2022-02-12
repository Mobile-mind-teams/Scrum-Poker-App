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

    @GET("sessions/{id}")
    fun getSessionByID(@Path("id") session_id: String): Call<SessionResponse>

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

    @GET("stories/project/all/{id}")
    fun getAllStoriesFromProject(@Path("id") project_id: String): Call<ProjectStoryResponse>

    @POST("stories/add/{document_id}&{story_id}&session")
    fun addStoryToSession(@Body sessionStory: SessionStory,
                          @Path("document_id") session_id: String,
                          @Path("story_id") story_id: String?
    ): Call<SessionStoriesResponse>

    @PATCH("sessions/update/{id}")
    fun updateSession(@Body session: Session, @Path("id") session_id : String) : Call<SessionResponse>

    @PATCH("stories/update/{document_id}&{story_id}&{collection}")
    fun updateStoryFrom(@Body story: SessionStory,
                        @Path("document_id") document_id : String,
                        @Path("story_id") story_id : String,
                        @Path("collection") collection : String,
    ) : Call<SessionStoriesResponse>

    @DELETE("table-cards/reset-table/{document_id}&{story_id}")
    fun clearTable(@Path("document_id") document_id : String,
                   @Path("story_id") story_id : String) : Call<TableCardResponse>

    @PATCH("table-cards/update/{document_id}&{story_id}")
    fun updateTableCards(@Body userCard: UserCard,
                         @Path("document_id") document_id : String,
                         @Path("story_id") story_id : String) : Call<TableCardResponse>

    @POST("backlogs/add")
    fun createBacklog(@Body backlog: Backlog): Call<BacklogResponse>

    @GET("stories/session/agreed/all/{id}")
    fun getAllStoriesForBacklog(
        @Path("id") doc_id: String,
    ): Call<SessionStoriesResponse>

    @POST("stories/add/{document_id}&{story_id}&backlog")
    fun addStoryToBacklog(@Body backlogStory: BacklogStory,
                          @Path("document_id") backlog_id: String,
                          @Path("story_id") story_id: String?
    ): Call<BacklogStoryResponse>
}