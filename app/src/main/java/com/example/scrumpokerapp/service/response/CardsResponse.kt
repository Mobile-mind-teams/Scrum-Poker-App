package com.example.scrumpokerapp.service.response

import com.example.scrumpokerapp.model.Card
import com.example.scrumpokerapp.model.RejectedErrors
import com.google.gson.annotations.SerializedName

class CardsResponse {
    @SerializedName("collection") var collection : String = ""
    @SerializedName("message") var message : String = ""
    @SerializedName("data") var data : List<Card> = listOf()

    fun toText(): String {
        return "CardsResponse => {\n" +
                "message: ${message},\n" +
                "collection: ${collection},\n" +
                "data: [${objectListToString(data)}],\n" +
                "}"
    }

    fun objectListToString(itemList: List<Card>): String{
        var list = ""
        for (item in itemList) {
            list = "\t\t${item.transformToJASONtxt()},\n${list}\t\t"
        }
        return list
    }
}