package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.BacklogStory
import com.google.gson.annotations.SerializedName

class BacklogStoryResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<BacklogStory> = listOf()
}