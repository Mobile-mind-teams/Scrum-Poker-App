package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.CallBackInfo
import com.example.scrumpokerapp.model.Email
import com.google.gson.annotations.SerializedName

class EmailResponse {
    @SerializedName("message") var message : String = ""
    @SerializedName("callback_info") var callbackInfo : CallBackInfo? = null

    fun toText(): String {
        return "EmailResponse => {\n" +
                "message: ${message},\n" +
                "callback_info: \n${callbackInfo?.transformToJASONtxt()},\n" +
                "}"
    }
}