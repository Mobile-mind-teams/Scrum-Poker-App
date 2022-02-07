package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class ProjectStory {
    @SerializedName("title") var title : String? = null
    @SerializedName("description") var description : String? = null
    @SerializedName("sid") var doc_id : String? = null

    fun transformToJASONtxt(): String {
        return "\tProjectStory => {\n" +
                "\ttitle: ${title},\n" +
                "\tdescription: ${description},\n" +
                "\tdoc_id: ${doc_id},\n" +
                "\t}"
    }
}