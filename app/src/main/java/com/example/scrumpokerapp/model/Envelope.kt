package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class Envelope {
    @SerializedName("from")
    var from: String? = null

    @SerializedName("to")
    var to: List<String> = listOf()

    constructor()

    fun transformToJASONtxt(): String {
        return "\t\tEnvelope => {\n" +
                "\t\tfrom: ${from},\n" +
                "\t\tto: [${listToString(to)}],\n" +
                "\t\t}"
    }

    fun listToString(stringList: List<String>): String{
        var list = ""
        for (email in stringList){
            list = "\n\t\t\t${email},${list}\t\t\t"
        }
        return if (list.isNotEmpty()) list else ""
    }
}