package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.SessionStory
import com.google.gson.annotations.SerializedName

class SessionStoriesResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<SessionStory> = listOf()

    constructor()

    constructor(collection: String, message: String, data: List<SessionStory>) {
        this.collection = collection
        this.message = message
        this.data = data
    }

    fun toText(): String {
        return "SessionStoriesResponse => {\n" +
                "message: ${message},\n" +
                "data: [${objectListToString(data)}],\n" +
                "}"
    }

    fun objectListToString(itemList: List<SessionStory>): String{
        var list = ""
        for (item in itemList) {
            list = "\t\t${item.transformToJASONtxt()},\n${list}\t\t"
        }
        return list
    }
}