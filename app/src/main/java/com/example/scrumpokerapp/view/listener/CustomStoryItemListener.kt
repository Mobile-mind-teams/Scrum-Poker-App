package com.example.scrumpokerapp.view.listener

import com.example.scrumpokerapp.model.BacklogStory

interface CustomStoryItemListener {
    fun getSelectedItem(story: BacklogStory)
}