package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class Project{
    @SerializedName("name")
    var name: String? = null

    @SerializedName("pid")
    var project_id: String? = null

    @SerializedName("sid")
    var session_id: String? = null

    @SerializedName("bid")
    var backlog_id: String? = null

    @SerializedName("status")
    var status: String? = null

    constructor()

    constructor(
        name: String?,
        project_id: String?,
        session_id: String?,
        backlog_id: String?,
        status: String?
    ) {
        this.project_id = project_id
        this.name = name
        this.session_id = session_id
        this.backlog_id = backlog_id
        this.status = status
    }

    constructor(
        project_id: String?,
        session_id: String?,
        status: String?
    ) {
        this.project_id = project_id
        this.session_id = session_id
        this.status = status
    }


    override fun toString(): String {
        return "Project => {\n" +
                "name: ${name},\n" +
                "project_id: ${project_id},\n" +
                "backlog_id: ${backlog_id},\n" +
                "session_id: ${session_id},\n" +
                "status: ${status}," +
                "\n}"
    }
}