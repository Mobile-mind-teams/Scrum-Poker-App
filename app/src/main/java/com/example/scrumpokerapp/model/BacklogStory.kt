package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class BacklogStory {
    @SerializedName("title") var title : String? = null
    @SerializedName("description") var description : String? = null
    @SerializedName("weight") var weight : String? = null
    @SerializedName("sid") var sid : String? = null

    override fun toString(): String {
        return "BacklogStroy => {\n" +
                "title: ${title},\n" +
                "description: ${description},\n" +
                "weight: ${weight},\n" +
                "sid: ${sid},\n" +
                "\n}"
    }

    fun toItemCard(): String {
        return "BacklogStroy => {\n" +
                "title: ${title},\n" +
                "description: ${description},\n" +
                "weight: ${weight},\n" +
                "sid: ${sid},\n" +
                "\n}"
    }
}