package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class EmailError{
    @SerializedName("code")
    var code: String? = null

    @SerializedName("response")
    var response: String? = null

    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("command")
    var command: Int? = null

    @SerializedName("rejected")
    var rejected: List<String> = listOf()

    @SerializedName("rejectedErrors")
    var rejectedErrors: List<RejectedErrors> = listOf()

    constructor()

    constructor(
        code: String?,
        response: String?,
        responseCode: Int?,
        command: Int?,
        rejected: List<String>,
        rejectedErrors: List<RejectedErrors>
    ) {
        this.code = code
        this.response = response
        this.responseCode = responseCode
        this.command = command
        this.rejected = rejected
        this.rejectedErrors = rejectedErrors
    }

    fun transformToJASONtxt(): String {
        return " \tEmailError => {\n" +
                "\tcode: ${code},\n" +
                "\tresponse: ${response},\n" +
                "\tresponseCode: ${responseCode},\n" +
                "\tcommand: ${command},\n" +
                "\trejected: [${listToString(rejected)}],\n" +
                "\trejectedErrors: \n [${objectListToString(rejectedErrors)},\n" +
                "\t]" +
                "\t}"
    }

    fun listToString(stringList: List<String>): String{
        var list = ""
        for (email in stringList){
            list = "\n\t\t${email},${list}\t\t"
        }
        return if (list.isNotEmpty()) list else ""
    }

    fun objectListToString(itemList: List<RejectedErrors>): String{
        var list = ""
        for (item in itemList){
            list = "\n\t\t${item.transformToJASONtxt()},${list}\t\t"
        }
        return list
    }
}