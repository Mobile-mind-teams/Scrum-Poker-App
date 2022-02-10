package com.example.scrumpokerapp.utils

import com.example.scrumpokerapp.model.*
import com.example.scrumpokerapp.persistance.UserProfile
import com.google.gson.annotations.SerializedName
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

    fun isTableCard(type: String): Boolean {
        return type == "tableCard"
    }

    fun convertCardListToUserCardList(cardList: List<Card>, user_id: String): List<UserCard> {
        var userCardList : ArrayList<UserCard> = arrayListOf()
        for (card in cardList){
            userCardList.add(
                UserCard(
                    card.name,
                    user_id,
                    card.value,
                    card.action,
                    false,
                    ""
                )
            )
        }
        return userCardList
    }
}