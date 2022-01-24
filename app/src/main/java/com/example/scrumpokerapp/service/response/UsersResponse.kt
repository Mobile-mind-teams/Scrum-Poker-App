package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.User
import com.google.gson.annotations.SerializedName

class UsersResponse () {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<User> = listOf()
}