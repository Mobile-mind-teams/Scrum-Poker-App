package com.example.scrumpokerapp.persistance

class UserProfile {

    var email: String? = null
    var password: String? = null
    var uid: String? = null
    var role: Int? = null
    var user_name: String? = null
    var doc_id: String? = null
    var status: String? = null

    constructor(email: String?,
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