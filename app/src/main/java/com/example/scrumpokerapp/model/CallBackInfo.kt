package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class CallBackInfo {
    @SerializedName("accepted")
    var acceptedList: List<String> = listOf()

    @SerializedName("rejected")
    var rejectedList: List<String> = listOf()

    @SerializedName("envelopeTime")
    var envelopeTime: Int? = null

    @SerializedName("messageTime")
    var messageTime: Int? = null

    @SerializedName("messageSize")
    var messageSize: Int? = null

    @SerializedName("response")
    var response: String? = null

    @SerializedName("messageId")
    var messageId: String? = null

    @SerializedName("envelope")
    var envelope: Envelope? = null

    @SerializedName("rejectedErrors")
    var rejectedErrors: List<RejectedErrors> = listOf()

    constructor()

    fun transformToJASONtxt(): String {
        return "\tCallBackInfo => {\n" +
                "\taccepted: [${listToString(acceptedList)}],\n" +
                "\trejected: [${listToString(rejectedList)}],\n" +
                objectListToString(rejectedErrors) +
                "\tenvelopeTime: ${envelopeTime},\n" +
                "\tmessageTime: ${messageTime},\n" +
                "\tmessageSize: ${messageSize},\n" +
                "\tresponse: ${response},\n" +
                "\tmessageId: ${messageId},\n" +
                "\tenvelope: \n ${envelope?.transformToJASONtxt()},\n" +
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
        for (item in itemList) {
            list = "\t\t${item.transformToJASONtxt()},\n${list}\t\t"
        }
        return list
    }
}