package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.UserCard
import com.google.gson.annotations.SerializedName

class TableCardResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<UserCard> = listOf()

    constructor()

    constructor(collection: String, message: String, data: List<UserCard>) {
        this.collection = collection
        this.message = message
        this.data = data
    }

    constructor(collection: String, message: String) {
        this.collection = collection
        this.message = message
    }

    fun toText(): String {
        return "TableCardResponse => {\n" +
                "message: ${message},\n" +
                "collection: ${collection},\n" +
                "data: [${objectListToString(data)}],\n" +
                "}"
    }

    fun objectListToString(itemList: List<UserCard>): String{
        var list = ""
        for (item in itemList) {
            list = "\t\t${item.transformToJASONtxt()},\n${list}\t\t"
        }
        return list
    }
}