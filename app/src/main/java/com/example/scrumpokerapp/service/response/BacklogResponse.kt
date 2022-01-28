package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.Backlog
import com.google.gson.annotations.SerializedName

class BacklogResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<Backlog> = listOf()
}