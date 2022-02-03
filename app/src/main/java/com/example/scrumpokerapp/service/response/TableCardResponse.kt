package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.UserCard
import com.google.gson.annotations.SerializedName

class TableCardResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<UserCard> = listOf()

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