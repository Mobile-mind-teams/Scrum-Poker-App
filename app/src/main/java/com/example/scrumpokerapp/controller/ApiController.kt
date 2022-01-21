package com.example.scrumpokerapp.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.service.ApiClient
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.service.response.AddUserResponse
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiController {
    val service = ApiClient().initRetrofit()
    val createUserMutableLiveData: MutableLiveData<AddUserResponse?>

    constructor(){
        createUserMutableLiveData = MutableLiveData()
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