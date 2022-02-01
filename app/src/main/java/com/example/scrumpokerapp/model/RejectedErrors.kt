package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class RejectedErrors {
    @SerializedName("code")
    var code: String? = null

    @SerializedName("response")
    var response: String? = null

    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("command")
    var command: Int? = null

    @SerializedName("recipient")
    var recipient: String? = null

    constructor()

    fun transformToJASONtxt(): String {
        return " \t\tRejectedErrors => {\n" +
                "\t\tcode: ${code},\n" +
                "\t\tresponse: ${response},\n" +
                "\t\tresponseCode: ${responseCode},\n" +
                "\t\tcommand: ${command},\n" +
                "\t\trecipient: ${recipient},\n" +
                "\t\t}"
    }
}