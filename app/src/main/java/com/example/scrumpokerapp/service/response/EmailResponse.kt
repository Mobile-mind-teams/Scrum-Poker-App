package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.CallBackInfo
import com.example.scrumpokerapp.model.Email
import com.example.scrumpokerapp.model.EmailError
import com.google.gson.annotations.SerializedName

class EmailResponse {
    @SerializedName("message") var message : String = ""
    @SerializedName("callback_info") var callbackInfo : CallBackInfo? = null
    @SerializedName("error") var errorInfo : EmailError? = null

    constructor() {
        this.message = "Not Send"
    }

    fun isSuccess(): Boolean{
        return message.equals("Success!")
    }

    fun toText(): String {
        return "EmailResponse => {\n" +
                "message: ${message},\n" +
                "callback_info: \n${callbackInfo?.transformToJASONtxt()},\n" +
                "}"
    }

    fun errorToText(): String {
        return "EmailResponse => {\n" +
                "message: ${message},\n" +
                "error: \n${errorInfo?.transformToJASONtxt()},\n" +
                "}"
    }
}