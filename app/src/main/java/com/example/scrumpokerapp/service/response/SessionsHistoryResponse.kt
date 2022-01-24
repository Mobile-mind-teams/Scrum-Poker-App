package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.SessionHistory
import com.google.gson.annotations.SerializedName

class SessionsHistoryResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<SessionHistory> = listOf()
}