package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.google.gson.annotations.SerializedName

data class AddUserResponse (
    @SerializedName("user") val value : UsersRegisterRequest = UsersRegisterRequest()
)