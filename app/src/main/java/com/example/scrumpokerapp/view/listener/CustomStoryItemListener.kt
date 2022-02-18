package com.example.scrumpokerapp.view.listener

import com.example.scrumpokerapp.model.BacklogStory
import com.example.scrumpokerapp.model.SessionStory

interface CustomStoryItemListener {
    fun getSelectedItem(story: BacklogStory)

    fun getSelectedItem(story: SessionStory)
}