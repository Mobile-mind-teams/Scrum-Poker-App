package com.example.scrumpokerapp.model

import com.example.scrumpokerapp.persistance.UserProfile
import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("uid")
    var uid: String? = null

    @SerializedName("role")
    var role: Int? = null

    @SerializedName("user_name")
    var user_name: String? = null

    @SerializedName("doc_id")
    var doc_id: String? = null

    constructor()

    constructor(
        email: String?,
        password: String?,
        uid: String?,
        role: Int?,
        user_name: String?,
        doc_id: String?
    ) {
        this.email = email
        this.password = password
        this.uid = uid
        this.role = role
        this.user_name = user_name
        this.doc_id = doc_id
    }

    constructor(email: String?, password: String?, uid: String?) {
        this.email = email
        this.password = password
        this.uid = uid
    }

    constructor(email: String?, uid: String?) {
        this.email = email
        this.uid = uid
    }

    override fun toString(): String {
        return "User => {\n" +
                "email: ${email},\n" +
                "password: ${password},\n" +
                "uid: ${uid},\n" +
                "role: ${role},\n" +
                "user_name: ${user_name},\n" +
                "doc_id: ${doc_id}," +
                "\n}"
    }

    fun isProductOwner(): Boolean {
        return UserProfile.role == 1
    }
}