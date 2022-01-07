package com.example.scrumpokerapp.persistance

import com.example.scrumpokerapp.model.User

object UserProfile {

    var email: String? = null
    var password: String? = null
    var uid: String? = null
    var role: Int? = null
    var user_name: String? = null

    val singletonUserProfile: User by lazy {
        User(email, password, uid, role, user_name)
    }

    init {

    }

    fun setUserProfile(email: String?,
                       password: String?,
                       uid: String?,
                       role: Int?,
                       user_name: String?,){
        this.email = email
        this.password = password
        this.uid = uid
        this.role = role
        this.user_name = user_name

    }

    fun isProductOwner(): Boolean {
        return role == 1
    }
}