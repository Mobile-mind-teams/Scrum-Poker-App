package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.Email
import com.google.gson.annotations.SerializedName

class EmailResponse {
    @SerializedName("message") var message : String = ""
}