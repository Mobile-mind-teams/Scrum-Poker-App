package com.example.scrumpokerapp.view.listener

import com.example.scrumpokerapp.model.Backlog

interface CustomBacklogListener {
    fun getSelectedBacklogItem(backlog: Backlog, longClick: Boolean)
}