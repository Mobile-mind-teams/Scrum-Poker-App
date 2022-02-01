package com.example.scrumpokerapp.view.listener

import com.example.scrumpokerapp.model.User

interface CustomUserItemListener {
    fun getSelectedUserItem(user: User)

    fun dropSelectedUserItem(user: User)
}