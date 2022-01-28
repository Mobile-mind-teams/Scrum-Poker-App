package com.example.scrumpokerapp.persistance

import com.example.scrumpokerapp.model.User

object UserProfile {

    var email: String? = null
    var password: String? = null
    var uid: String? = null
    var role: Int? = null
    var user_name: String? = null
    var doc_id: String? = null
    var status: String? = null

    val singletonUserProfile: User by lazy {
        User(email, password, uid, role, user_name, doc_id, status)
    }

    init {

    }

    fun setUserProfile(email: String?,
                       password: String?,
                       uid: String?,
                       role: Int?,
                       user_name: String?,
                       doc_id: String?,
                       status: String?){
        this.email = email
        this.password = password
        this.uid = uid
        this.role = role
        this.user_name = user_name
        this.doc_id = doc_id
        this.status = status
    }

    fun isProductOwner(): Boolean {
        return role == 1
    }

    fun isAvailable(): Boolean {
        return status == "available"
    }

    override fun toString(): String {
        return "User => {\n" +
                "email: ${email},\n" +
                "password: ${password},\n" +
                "uid: ${uid},\n" +
                "role: ${role},\n" +
                "user_name: ${user_name},\n" +
                "doc_id: ${doc_id}," +
                "status: ${status}" +
                "\n}"
    }
}