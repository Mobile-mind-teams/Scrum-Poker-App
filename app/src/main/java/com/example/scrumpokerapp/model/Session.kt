package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class Session {
    @SerializedName("project_id") var project_id : String? = null
    @SerializedName("project_name") var project_name : String? = null
    @SerializedName("started_at") var started_at : String? = null
    @SerializedName("finished_at") var finished_at : String? = null
    @SerializedName("status") var status : String? = null
    @SerializedName("session_id") var session_id : String? = null
    @SerializedName("note") var note : String? = null

    constructor(){}

    constructor(project_name: String, project_id: String, session_id: String, status: String){
        this.project_name = project_name
        this.project_id = project_id
        this.session_id = session_id
        this.status = status
    }

    override fun toString(): String {
        return "Session => {\n" +
                "project_id: ${project_id},\n" +
                "project_name: ${project_name},\n" +
                "started_at: ${started_at},\n" +
                "finished_at: ${finished_at},\n" +
                "status: ${status},\n" +
                "session_id: ${session_id},\n" +
                "note: ${note},\n" +
                "\n}"
    }

    fun toItemCard(): String {
        return "Session => {\n" +
                "status: ${status},\n" +
                "project_name: ${project_name},\n" +
                "project_id: ${project_id},\n" +
                "session_id: ${session_id},\n" +
                "\n}"
    }
}