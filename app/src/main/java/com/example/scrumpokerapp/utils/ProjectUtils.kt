package com.example.scrumpokerapp.utils

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
}