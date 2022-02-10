package com.example.scrumpokerapp.view.listener

import com.example.scrumpokerapp.model.UserCard

interface CustomCardItemListener {
    fun getSelectedCardItem(userCard: UserCard, type: String)
}