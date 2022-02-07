package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.ProjectStory
import com.google.gson.annotations.SerializedName

class ProjectStoryResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<ProjectStory> = listOf()

    fun toText(): String {
        return "ProjectStoryResponse => {\n" +
                "message: ${message},\n" +
                "data: [${objectListToString(data)}],\n" +
                "}"
    }

    fun objectListToString(itemList: List<ProjectStory>): String{
        var list = ""
        for (item in itemList) {
            list = "\t\t${item.transformToJASONtxt()},\n${list}\t\t"
        }
        return list
    }
}