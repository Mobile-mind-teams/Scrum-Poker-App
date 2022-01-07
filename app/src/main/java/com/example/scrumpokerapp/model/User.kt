package com.example.scrumpokerapp.model

class User {
    var email: String? = null
    var password: String? = null
    var uid: String? = null
    var role: Int? = null
    var user_name: String? = null

    constructor()

    constructor(
        email: String?,
        password: String?,
        uid: String?,
        role: Int?,
        user_name: String?
    ) {
        this.email = email
        this.password = password
        this.uid = uid
        this.role = role
        this.user_name = user_name
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
}