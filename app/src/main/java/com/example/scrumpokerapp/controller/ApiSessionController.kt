package com.example.scrumpokerapp.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.service.ApiSessionClient
import com.example.scrumpokerapp.service.response.SessionResponse
import com.example.scrumpokerapp.service.response.SessionsHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiSessionController {
    val service = ApiSessionClient().initRetrofit()
    val sessionHistoryResponseMutableLiveData : MutableLiveData<SessionsHistoryResponse?>
    val sessionResponseMutableLiveData : MutableLiveData<SessionResponse?>

    constructor(){
        sessionHistoryResponseMutableLiveData = MutableLiveData()
        sessionResponseMutableLiveData = MutableLiveData()
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

    fun getAllSessions(){
        service.getAllSessions().enqueue(object : Callback<SessionResponse> {
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

}