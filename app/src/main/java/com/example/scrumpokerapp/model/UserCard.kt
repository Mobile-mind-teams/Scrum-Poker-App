package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class UserCard {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("user_id")
    var user_id: String? = null

    @SerializedName("value")
    var value: Double? = null

    @SerializedName("action")
    var action: String? = null

    @SerializedName("visibility")
    var visibility: Boolean? = null

    @SerializedName("story_id")
    var story_id: String? = null

    @SerializedName("doc_id")
    var doc_id: String? = null

    constructor()

    constructor(
        user_id: String?,
        value: Double?,
        action: String?,
        visibility: Boolean?,
        story_id: String?,
        name: String?,
        doc_id: String?
    ) {
        this.user_id = user_id
        this.value = value
        this.action = action
        this.visibility = visibility
        this.story_id = story_id
        this.name = name
        this.doc_id = doc_id
    }


    fun transformToJASONtxt(): String {
        return "UserCard => {\n" +
                "user_id: ${user_id},\n" +
                "value: ${value},\n" +
                "action: ${action},\n" +
                "visibility: ${visibility},\n" +
                "story_id: ${story_id},\n" +
                "name: ${name},\n" +
                "}"
    }
}