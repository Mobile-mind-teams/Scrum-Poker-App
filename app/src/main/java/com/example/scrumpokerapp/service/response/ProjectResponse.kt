package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.Project
import com.google.gson.annotations.SerializedName

class ProjectResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<Project> = listOf()
}