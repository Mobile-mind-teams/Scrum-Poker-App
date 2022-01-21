package com.example.scrumpokerapp.service.request

import com.google.gson.annotations.SerializedName

class UsersRegisterRequest (
    @SerializedName("email") val email : String = "",
    @SerializedName("password") val password : String = "",
    @SerializedName("role") val role : Int = 0,
    @SerializedName("uid") var uid : String = "",
    @SerializedName("user_name") val user_name : String = ""
)