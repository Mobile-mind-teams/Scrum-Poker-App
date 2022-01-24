package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.Session
import com.google.gson.annotations.SerializedName

class SessionResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<Session> = listOf()
}