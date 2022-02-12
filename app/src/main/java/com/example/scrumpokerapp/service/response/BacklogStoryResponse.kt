package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.BacklogStory
import com.example.scrumpokerapp.model.SessionStory
import com.google.gson.annotations.SerializedName

class BacklogStoryResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<BacklogStory> = listOf()

    constructor()

    constructor(collection: String, message: String, data: List<BacklogStory>) {
        this.collection = collection
        this.message = message
        this.data = data
    }

    fun toText(): String {
        return "BacklogStoryResponse => {\n" +
                "message: ${message},\n" +
                "data: [${objectListToString(data)}],\n" +
                "}"
    }

    fun objectListToString(itemList: List<BacklogStory>): String{
        var list = ""
        for (item in itemList) {
            list = "\t\t${item.transformToJASONtxt()},\n${list}\t\t"
        }
        return list
    }
}