package com.example.scrumpokerapp.utils

import com.example.scrumpokerapp.model.ProjectStory
import com.example.scrumpokerapp.model.SessionStory
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.persistance.UserProfile
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ProjectUtils {
    fun generateTimeStamp(): String{
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    }

    fun isProjectOwner(role: Int?): Boolean{
        return role == 1
    }

    fun isAvailable(status: String?): Boolean {
        return status == "available"
    }

    fun showCreateSession(user: UserProfile?): Boolean {
        return isProjectOwner(user?.role) && isAvailable(user?.status)
    }

    fun getRoleAsString(role: Int?): String {
        return if (role==1){
            "po"
        } else "user"
    }

    fun convertProjectStoriesToSessionStories(list: List<ProjectStory>): List<SessionStory>{
        var sessionStoryList: ArrayList<SessionStory> = arrayListOf()
        for (item in list){
            sessionStoryList.add(
                SessionStory(
                    item.title,
                    item.description,
                    item.doc_id
                )
            )
        }
        return sessionStoryList
    }
}