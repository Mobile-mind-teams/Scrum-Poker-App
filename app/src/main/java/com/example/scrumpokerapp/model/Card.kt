package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class Card {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("value")
    var value: Double? = null

    @SerializedName("action")
    var action: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("cid")
    var doc_id: String? = null

    constructor()

    fun transformToJASONtxt(): String {
        return "\tCard => {\n" +
                "\tname: ${name},\n" +
                "\tvalue: ${value},\n" +
                "\taction: ${action},\n" +
                "\ttype: ${type},\n" +
                "\tstatus: ${status},\n" +
                "\tdoc_id: ${doc_id},\n" +
                "\t}"
    }
}