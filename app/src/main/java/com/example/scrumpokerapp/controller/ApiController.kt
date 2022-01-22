package com.example.scrumpokerapp.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.service.ApiClient
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.service.response.AddUserResponse
import com.example.scrumpokerapp.service.response.GetUsersResponse
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiController {
    val service = ApiClient().initRetrofit()
    val createUserMutableLiveData: MutableLiveData<AddUserResponse?>
    val userMutableLiveData: MutableLiveData<GetUsersResponse?>

    constructor(){
        createUserMutableLiveData = MutableLiveData()
        userMutableLiveData = MutableLiveData()
    }

    fun getUserByIdApi(uid: String){
        service.getUserById(uid).enqueue(object : Callback<GetUsersResponse>{
            override fun onResponse(
                call: Call<GetUsersResponse>,
                response: Response<GetUsersResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    Log.i("User Data: ","GET: " + 200 + " " + response.body())
                } else {
                    Log.i("User Data: ", "GET: " + 200 + " Not Found!")
                }

                userMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<GetUsersResponse>, t: Throwable) {
                userMutableLiveData.postValue(null)
                Log.i("User Data: ","GET: " + 500 + " " + t.stackTraceToString())
            }
        })
    }

    fun postUsersApi(usersRegisterRequest: UsersRegisterRequest) {
        service.postUsers(usersRegisterRequest).enqueue(object : Callback<AddUserResponse>{
            override fun onResponse(
                call: Call<AddUserResponse>,
                response: Response<AddUserResponse>
            ) {
                if (response.isSuccessful){
                    Log.i("User Data: ","POST: " + 200 + " " + response.body()?.value?.uid)
                } else {
                    Log.i("User Data: ","POST: " + 200 + " Nothing added!")
                }

                createUserMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                createUserMutableLiveData.postValue(null)
                Log.i("User Data: ","POST: " + 500 + " " + t.stackTrace.toString())
            }
        })
    }
}