package com.example.scrumpokerapp.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.service.ApiClient
import com.example.scrumpokerapp.service.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiController {
    val backlogResponseMutableLiveData: MutableLiveData<BacklogResponse?>
    val service = ApiClient().initRetrofit()
    val userResponseMutableLiveData : MutableLiveData<UsersResponse?>
    val sessionHistoryResponseMutableLiveData : MutableLiveData<SessionsHistoryResponse?>
    val sessionResponseMutableLiveData : MutableLiveData<SessionResponse?>
    val backlogStoriesResponseMutableLiveData : MutableLiveData<BacklogStoryResponse?>

    constructor(){
        userResponseMutableLiveData = MutableLiveData()
        sessionHistoryResponseMutableLiveData = MutableLiveData()
        sessionResponseMutableLiveData = MutableLiveData()
        backlogResponseMutableLiveData = MutableLiveData()
        backlogStoriesResponseMutableLiveData = MutableLiveData()
    }

    fun getUserByIdApi(uid: String){
        service.getUserById(uid).enqueue(object : Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("${response.body()?.collection} Data GET: "," ${response.body()?.message} " + 200 + " " + response.body()?.data?.get(0))

                    UserProfile.setUserProfile(
                        response.body()?.data?.get(0)?.email.toString(),
                        response.body()?.data?.get(0)?.password.toString(),
                        response.body()?.data?.get(0)?.uid.toString(),
                        response.body()?.data?.get(0)?.role.toString().toInt(),
                        response.body()?.data?.get(0)?.user_name.toString(),
                        response.body()?.data?.get(0)?.doc_id.toString(),
                        response.body()?.data?.get(0)?.status.toString()
                    )

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

                userResponseMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                userResponseMutableLiveData.postValue(null)
                Log.i("User Data: ","POST: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }

    fun getAllUserSessions(uid: String){
        service.getAllUserSessions(uid).enqueue(object : Callback<SessionsHistoryResponse> {
            override fun onResponse(
                call: Call<SessionsHistoryResponse>,
                historyResponse: Response<SessionsHistoryResponse>
            ) {
                if (historyResponse.isSuccessful && historyResponse.body() != null){
                    Log.i("${historyResponse.body()?.collection} Data GET: "," ${historyResponse.body()?.message} " + 200 + " " + historyResponse.body()?.data)
                } else {
                    Log.i("${historyResponse.body()?.collection} Data GET: ", "${historyResponse.body()?.message} " + 200 + " Not Found!")
                }

                sessionHistoryResponseMutableLiveData.postValue(historyResponse.body())
            }

            override fun onFailure(call: Call<SessionsHistoryResponse>, t: Throwable) {
                sessionHistoryResponseMutableLiveData.postValue(null)
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

}