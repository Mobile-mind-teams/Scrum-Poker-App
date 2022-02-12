package com.example.scrumpokerapp.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.model.*
import com.example.scrumpokerapp.service.ApiClient
import com.example.scrumpokerapp.service.response.*
import com.example.scrumpokerapp.utils.ProjectUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiController {
    val systemStatusMutableLiveData: MutableLiveData<Boolean>
    val backlogResponseMutableLiveData: MutableLiveData<BacklogResponse?>
    val service = ApiClient().initRetrofit()
    val adminResponseMutableLiveData : MutableLiveData<UsersResponse?>
    val userRegisterResponseMutableLiveData : MutableLiveData<Boolean>
    val userUpdateResponseMutableLiveData : MutableLiveData<UsersResponse?>
    val userListResponseMutableLiveData : MutableLiveData<UsersResponse?>
    val userResponseMutableLiveData : MutableLiveData<UsersResponse?>
    val userLoginResponseMutableLiveData : MutableLiveData<UsersResponse?>
    val sessionResponseMutableLiveData : MutableLiveData<SessionResponse?>
    val currentSessionResponseMutableLiveData : MutableLiveData<SessionResponse?>
    val newSessionResponseMutableLiveData : MutableLiveData<SessionResponse?>
    val backlogStoriesResponseMutableLiveData : MutableLiveData<BacklogStoryResponse?>
    val projectResponseMutableLiveData : MutableLiveData<ProjectResponse?>
    val projectUpdateResponseMutableLiveData : MutableLiveData<ProjectResponse?>
    val emailResponseMutableLiveData : MutableLiveData<EmailResponse?>
    val cardsResponseMutableLiveData : MutableLiveData<CardsResponse?>
    val sessionStoriesResponseMutableLiveData : MutableLiveData<SessionStoriesResponse?>
    val tableCardResponseMutableLiveData : MutableLiveData<TableCardResponse?>
    val storySessionListMutableLiveData : MutableLiveData<SessionStoriesResponse?>
    val projectStoryListMutableLiveData : MutableLiveData<ProjectStoryResponse?>
    val sessionUpdateMutableLiveData : MutableLiveData<SessionResponse?>
    val currentStoryMutableLiveData : MutableLiveData<SessionStoriesResponse?>
    val clearTableMutableLiveData : MutableLiveData<TableCardResponse?>


    constructor(){
        userResponseMutableLiveData = MutableLiveData()
        adminResponseMutableLiveData = MutableLiveData()
        sessionResponseMutableLiveData = MutableLiveData()
        newSessionResponseMutableLiveData = MutableLiveData()
        backlogResponseMutableLiveData = MutableLiveData()
        backlogStoriesResponseMutableLiveData = MutableLiveData()
        projectResponseMutableLiveData = MutableLiveData()
        emailResponseMutableLiveData = MutableLiveData()
        systemStatusMutableLiveData = MutableLiveData()
        projectUpdateResponseMutableLiveData = MutableLiveData()
        userUpdateResponseMutableLiveData = MutableLiveData()
        userListResponseMutableLiveData = MutableLiveData()
        userRegisterResponseMutableLiveData = MutableLiveData()
        userLoginResponseMutableLiveData = MutableLiveData()
        cardsResponseMutableLiveData = MutableLiveData()
        sessionStoriesResponseMutableLiveData = MutableLiveData()
        tableCardResponseMutableLiveData = MutableLiveData()
        storySessionListMutableLiveData = MutableLiveData()
        projectStoryListMutableLiveData = MutableLiveData()
        sessionUpdateMutableLiveData = MutableLiveData()
        currentSessionResponseMutableLiveData = MutableLiveData()
        currentStoryMutableLiveData = MutableLiveData()
        clearTableMutableLiveData = MutableLiveData()
    }

    fun getUserByIdApi(uid: String){
        service.getUserById(uid).enqueue(object : Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data?.get(0))
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                userResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                userResponseMutableLiveData.postValue(null)
                Log.i("User Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun postUsersApi(usersRegisterRequest: User) {
        service.postUsers(usersRegisterRequest).enqueue(object : Callback<UsersResponse>{

            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful){
                    Log.i("${response.body()?.collection} Data POST: "," ${response.body()?.message} " + 200 + " " + response.body()?.data?.get(0))
                } else {
                    Log.i("${response.body()?.collection} Data POST: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                userRegisterResponseMutableLiveData.postValue(response.isSuccessful)
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                userRegisterResponseMutableLiveData.postValue(false)
                Log.i("User Data: ","POST: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun getAllUserSessions(email: String){
        service.getAllUserSessionsByEmail(email).enqueue(object : Callback<SessionResponse> {
            override fun onResponse(
                call: Call<SessionResponse>,
                response: Response<SessionResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                sessionResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                sessionResponseMutableLiveData.postValue(null)
                Log.i("Session Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun getAllSessions(po_id: String){
        service.getAllSessionsByPOID(po_id).enqueue(object : Callback<SessionResponse> {
            override fun onResponse(
                call: Call<SessionResponse>,
                response: Response<SessionResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                sessionResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                sessionResponseMutableLiveData.postValue(null)
                Log.i("Session Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun getAllUsers(){
        service.getAllUsers().enqueue(object : Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                userResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                userResponseMutableLiveData.postValue(null)
                Log.i("User Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun getAllAvailableUsers(){
        service.getAllAvailableUsers().enqueue(object : Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                userListResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                userListResponseMutableLiveData.postValue(null)
                Log.i("User Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun getAllUnassignedProjects(){
        service.getAllUnassignedProjects().enqueue(object : Callback<ProjectResponse>{
            override fun onResponse(
                call: Call<ProjectResponse>,
                response: Response<ProjectResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                projectResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                projectResponseMutableLiveData.postValue(null)
                Log.i("Project Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun createNewSession(session: Session){
        service.createSession(session).enqueue(object : Callback<SessionResponse>{
            override fun onResponse(
                call: Call<SessionResponse>,
                response: Response<SessionResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data POST: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data POST: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                sessionResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                sessionResponseMutableLiveData.postValue(null)
                Log.i("Session Data: ","POST: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun addStoriesToSession(
        storySessionList: List<SessionStory>,
        session_id: String
    ){
        var storySessionListResponse: ArrayList<SessionStory?> = arrayListOf()
        var collection: String = ""
        var message: String = ""
        var isSuccessfulProcess: Boolean = true
        var index = 0
        for(sessionStory in storySessionList){
            service.addStoryToSession(sessionStory, session_id, sessionStory.doc_id).enqueue(object : Callback<SessionStoriesResponse>{
                override fun onResponse(
                    call: Call<SessionStoriesResponse>,
                    response: Response<SessionStoriesResponse>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        Log.i("${response.body()?.collection} Data POST: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                    } else {
                        Log.i("${response.body()?.collection} Data POST: ", "${response.body()?.message} " + 200 + " Not Found!")
                    }

                    if (collection == "" && message == ""){
                        collection = response.body()?.collection.toString()
                        message = response.body()?.message.toString()
                    }

                    storySessionListResponse.add(response.body()?.data?.get(index))
                }

                override fun onFailure(call: Call<SessionStoriesResponse>, t: Throwable) {
                    isSuccessfulProcess = false
                    Log.i("Session Story Data: ","POST: " + 500 + " " + t.stackTraceToString())
                }
            })
        }

        if (isSuccessfulProcess){
            storySessionListMutableLiveData.postValue(
                SessionStoriesResponse(
                    collection,
                    message,
                    storySessionList
                )
            )
        } else {
            storySessionListMutableLiveData.postValue(null)
        }
    }

    fun getAllProjectStories(project_id: String){
        service.getAllStoriesFromProject(project_id).enqueue(object : Callback<ProjectStoryResponse>{
            override fun onResponse(
                call: Call<ProjectStoryResponse>,
                response: Response<ProjectStoryResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                projectStoryListMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<ProjectStoryResponse>, t: Throwable) {
                projectStoryListMutableLiveData.postValue(null)
                Log.i("Project Story List Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun getAllBacklogs() {
        service.getAllBacklogs().enqueue(object : Callback<BacklogResponse>{
            override fun onResponse(
                call: Call<BacklogResponse>,
                response: Response<BacklogResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                backlogResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<BacklogResponse>, t: Throwable) {
                backlogResponseMutableLiveData.postValue(null)
                Log.i("Backlog Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun getAllStoriesFromBacklog(doc_id: String){
        service.getAllStoriesFromBacklog(doc_id).enqueue(object : Callback<BacklogStoryResponse> {

            override fun onResponse(
                call: Call<BacklogStoryResponse>,
                response: Response<BacklogStoryResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                backlogStoriesResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<BacklogStoryResponse>, t: Throwable) {
                backlogStoriesResponseMutableLiveData.postValue(null)
                Log.i("Backlog Story Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun sendEmailApi(email: Email) {
        service.sendEmail(email).enqueue(object : Callback<EmailResponse>{

            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.isSuccessful){
                    Log.i("Email Data POST: "," ${response.body()?.message} " + 200 + " " + response.body()?.message)
                } else {
                    Log.i("Email Data POST: ", "${response.body()?.message} " + 200 + "Api Error!")
                }

                emailResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                emailResponseMutableLiveData.postValue(EmailResponse())
                Log.i("Email Data: ","POST: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun updateUser(user: User, doc_id: String, role: Int?){
        service.updateUser(user, doc_id).enqueue(object : Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data PATCH: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data PATCH: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                if (ProjectUtils().isProjectOwner(role)){
                    adminResponseMutableLiveData.postValue(response.body())
                } else {
                    userUpdateResponseMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                if (ProjectUtils().isProjectOwner(role)){
                    adminResponseMutableLiveData.postValue(null)
                } else {
                    userUpdateResponseMutableLiveData.postValue(null)
                }
                Log.i("User Data: ","PATCH: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun updateProject(project: Project){
        service.updateProject(project, project.project_id.toString()).enqueue(object : Callback<ProjectResponse>{
            override fun onResponse(
                call: Call<ProjectResponse>,
                response: Response<ProjectResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data PATCH: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data PATCH: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                projectUpdateResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                projectUpdateResponseMutableLiveData.postValue(null)
                Log.i("Project Data: ","PATCH: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun getJustCreatedSession(admin_uid : String, status: String){
        service.getSessionByAdminIDAndStatus(admin_uid, status).enqueue(object : Callback<SessionResponse>{
            override fun onResponse(
                call: Call<SessionResponse>,
                response: Response<SessionResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                newSessionResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                newSessionResponseMutableLiveData.postValue(null)
                Log.i("Session Data: ","GET: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun getDeckByUserRole(role_string: String){
        service.getDeckFor(role_string).enqueue(object : Callback<CardsResponse>{
            override fun onResponse(call: Call<CardsResponse>, response: Response<CardsResponse>) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                cardsResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<CardsResponse>, t: Throwable) {
                cardsResponseMutableLiveData.postValue(null)
                Log.i("Card Data: ","GET: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun getStoryListBySessionID(session_id: String){
        service.getAllSessionStories(session_id).enqueue(object : Callback<SessionStoriesResponse>{
            override fun onResponse(
                call: Call<SessionStoriesResponse>,
                response: Response<SessionStoriesResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                sessionStoriesResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionStoriesResponse>, t: Throwable) {
                sessionStoriesResponseMutableLiveData.postValue(null)
                Log.i("Session Stories Data: ","GET: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun setTableCard(card: UserCard, session_id: String){
        service.setTableCard(card, session_id).enqueue(object : Callback<TableCardResponse>{
            override fun onResponse(
                call: Call<TableCardResponse>,
                response: Response<TableCardResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data POST: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data POST: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                tableCardResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<TableCardResponse>, t: Throwable) {
                tableCardResponseMutableLiveData.postValue(null)
                Log.i("Table Card Data: ","POST: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun updateSession(session: Session){
        service.updateSession(session, session.session_id!!).enqueue(object : Callback<SessionResponse>{
            override fun onResponse(
                call: Call<SessionResponse>,
                response: Response<SessionResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data PATCH: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data PATCH: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                sessionUpdateMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                sessionUpdateMutableLiveData.postValue(null)
                Log.i("Session Data: ","PATCH: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun getSessionByID(session_id: String){
        service.getSessionByID(session_id).enqueue(object : Callback<SessionResponse>{
            override fun onResponse(
                call: Call<SessionResponse>,
                response: Response<SessionResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                currentSessionResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                currentSessionResponseMutableLiveData.postValue(null)
                Log.i("Session Data: ","GET: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun updateStoryFrom(story: SessionStory, document_id: String, collection: String){
        service.updateStoryFrom(story, document_id, story.doc_id!!, collection).enqueue(object : Callback<SessionStoriesResponse>{
            override fun onResponse(
                call: Call<SessionStoriesResponse>,
                response: Response<SessionStoriesResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data PATCH: "," ${response.body()?.message} " + 200 + " " + response.body()?.toText())
                } else {
                    Log.i("${response.body()?.collection} Data PATCH: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                currentStoryMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionStoriesResponse>, t: Throwable) {
                currentStoryMutableLiveData.postValue(null)
                Log.i("Story Session Data: ","PATCH: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun clearTable(sessionId: String, story_id: String) {
        service.clearTable(sessionId, story_id).enqueue(object : Callback<TableCardResponse>{
            override fun onResponse(
                call: Call<TableCardResponse>,
                response: Response<TableCardResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data DELETE: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data DELETE: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                clearTableMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<TableCardResponse>, t: Throwable) {
                clearTableMutableLiveData.postValue(null)
                Log.i("Table Card Data: ","DELETE: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun flipCards(session_id: String, tableCards: List<UserCard>) {
        var tableCardsListResponse: ArrayList<UserCard> = arrayListOf()
        var collection: String = ""
        var message: String = ""
        var isSuccessfulProcess: Boolean = true
        var index = 0
        for(card in tableCards){
            card.visibility = true
            service.updateTableCards(card, session_id, card.doc_id!!).enqueue(object : Callback<TableCardResponse>{
                override fun onResponse(
                    call: Call<TableCardResponse>,
                    response: Response<TableCardResponse>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        Log.i("${response.body()?.collection} Data PATCH: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                    } else {
                        Log.i("${response.body()?.collection} Data PATCH: ", "${response.body()?.message} " + 200 + " Not Found!")
                    }

                    if (collection == "" && message == ""){
                        collection = response.body()?.collection.toString()
                        message = response.body()?.message.toString()
                    }

                    tableCardsListResponse.add(response.body()?.data?.get(index)!!)
                }

                override fun onFailure(call: Call<TableCardResponse>, t: Throwable) {
                    isSuccessfulProcess = false
                    Log.i("Table Card Data: ","PATCH: " + 500 + " " + t.stackTraceToString())
                }
            })
        }

        if (isSuccessfulProcess){
            tableCardResponseMutableLiveData.postValue(
                TableCardResponse(
                    collection,
                    message,
                    tableCardsListResponse
                )
            )
        } else {
            tableCardResponseMutableLiveData.postValue(null)
        }
    }

    fun updateTableCard(session_id: String, userCard: UserCard) {
        service.updateTableCards(userCard, session_id, userCard.doc_id!!).enqueue(object : Callback<TableCardResponse>{
            override fun onResponse(
                call: Call<TableCardResponse>,
                response: Response<TableCardResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data PATCH: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data PATCH: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                tableCardResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<TableCardResponse>, t: Throwable) {
                tableCardResponseMutableLiveData.postValue(null)
                Log.i("Table Card Data: ","PATCH: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun createBacklogAPI(backlog: Backlog){
        service.createBacklog(backlog).enqueue(object : Callback<BacklogResponse>{
            override fun onResponse(
                call: Call<BacklogResponse>,
                response: Response<BacklogResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data POST: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data POST: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                backlogResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<BacklogResponse>, t: Throwable) {
                backlogResponseMutableLiveData.postValue(null)
                Log.i("Backlopg Data: ","POST: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun getStoriesForBacklog(session_id: String){
        service.getAllStoriesForBacklog(session_id).enqueue(object : Callback<SessionStoriesResponse>{
            override fun onResponse(
                call: Call<SessionStoriesResponse>,
                response: Response<SessionStoriesResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                } else {
                    Log.i("${response.body()?.collection} Data GET: ", "${response.body()?.message} " + 200 + " Not Found!")
                }

                sessionStoriesResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<SessionStoriesResponse>, t: Throwable) {
                sessionStoriesResponseMutableLiveData.postValue(null)
                Log.i("Session Story List Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun addStoriesToBacklog(storyList: List<SessionStory>, docId: String) {
        var backlogStoryList : List<BacklogStory> = ProjectUtils().convertSessionStoriesToBacklogStories(storyList)
        var backlogStoryListResponse : ArrayList<BacklogStory> = arrayListOf()
        var message: String = ""
        var collection: String = ""
        var isSuccessfulProcess: Boolean = true
        var index = 0
        for (backlogStory in backlogStoryList){
            service.addStoryToBacklog(backlogStory, docId, backlogStory.sid).enqueue(object : Callback<BacklogStoryResponse>{
                override fun onResponse(
                    call: Call<BacklogStoryResponse>,
                    response: Response<BacklogStoryResponse>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        Log.i("${response.body()?.collection} Data POST: "," ${response.body()?.message} " + 200 + " " + response.body()?.data)
                    } else {
                        Log.i("${response.body()?.collection} Data POST: ", "${response.body()?.message} " + 200 + " Not Found!")
                    }

                    if (collection == "" && message == ""){
                        collection = response.body()?.collection.toString()
                        message = response.body()?.message.toString()
                    }

                    backlogStoryListResponse.add(response.body()?.data?.get(index)!!)
                }

                override fun onFailure(call: Call<BacklogStoryResponse>, t: Throwable) {
                    isSuccessfulProcess = false
                    Log.i("Session Story Data: ","POST: " + 500 + " " + t.stackTraceToString())
                }
            })
        }

        if (isSuccessfulProcess){
            backlogStoriesResponseMutableLiveData.postValue(
                BacklogStoryResponse(
                    collection,
                    message,
                    backlogStoryListResponse
                )
            )
        } else {
            storySessionListMutableLiveData.postValue(null)
        }
    }

}