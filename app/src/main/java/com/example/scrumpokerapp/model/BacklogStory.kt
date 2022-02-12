package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class BacklogStory {
    @SerializedName("title") var title : String? = null
    @SerializedName("description") var description : String? = null
    @SerializedName("weight") var weight : Double? = null
    @SerializedName("sid") var sid : String? = null

    constructor()

    constructor(title: String?, description: String?, weight: Double?, sid: String?) {
        this.title = title
        this.description = description
        this.weight = weight
        this.sid = sid
    }

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

    fun transformToJASONtxt(): String {
        return "\tBacklogStroy => {\n" +
                "\ttitle: ${title},\n" +
                "\tdescription: ${description},\n" +
                "\tweight: ${weight},\n" +
                "\tdoc_id: ${sid},\n" +
                "\t}"
    }
}