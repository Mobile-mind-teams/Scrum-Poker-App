package com.example.scrumpokerapp.view.listener

import com.example.scrumpokerapp.model.User

interface CustomUserItemListener {
    fun getSelectedItemEmail(email: String)

    fun dropSelectedItemEmail(email: String)
}