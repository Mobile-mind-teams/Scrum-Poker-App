package com.example.scrumpokerapp.persistance

import com.example.scrumpokerapp.model.User

object UserProfile {

    var email: String? = null
    var password: String? = null
    var uid: String? = null
    var role: Int? = null
    var user_name: String? = null
    var doc_id: String? = null

    val singletonUserProfile: User by lazy {
        User(email, password, uid, role, user_name, doc_id)
    }

    init {

    }

    fun setUserProfile(email: String?,
                       password: String?,
                       uid: String?,
                       role: Int?,
                       user_name: String?,
                       doc_id: String?,){
        this.email = email
        this.password = password
        this.uid = uid
        this.role = role
        this.user_name = user_name
        this.doc_id = doc_id
    }

    fun isProductOwner(): Boolean {
        return role == 1
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
}