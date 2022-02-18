package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class Backlog {
    @SerializedName("created_at") var created_at : String? = null
    @SerializedName("modified_at") var modified_at : String? = null
    @SerializedName("project_id") var project_id : String? = null
    @SerializedName("project_name") var project_name : String? = null
    @SerializedName("status") var status : String? = null
    @SerializedName("sesion_id") var session_id : String? = null
    @SerializedName("bid") var doc_id : String? = null

    constructor(){}

    constructor(
        modified_at: String?,
        project_id: String?,
        project_name: String?,
        status: String?,
        session_id: String?
    ) {
        this.modified_at = modified_at
        this.project_id = project_id
        this.project_name = project_name
        this.status = status
        this.session_id = session_id
    }

    constructor(
        created_at: String?,
        modified_at: String?,
        project_id: String?,
        project_name: String?,
        status: String?,
        session_id: String?
    ) {
        this.created_at = created_at
        this.modified_at = modified_at
        this.project_id = project_id
        this.project_name = project_name
        this.status = status
        this.session_id = session_id
    }

    constructor(
        created_at: String?,
        modified_at: String?,
        project_id: String?,
        project_name: String?,
        status: String?,
        session_id: String?,
        doc_id: String?
    ) {
        this.created_at = created_at
        this.modified_at = modified_at
        this.project_id = project_id
        this.project_name = project_name
        this.status = status
        this.session_id = session_id
        this.doc_id = doc_id
    }


    override fun toString(): String {
        return "Backlog => {\n" +
                "created_at: ${created_at},\n" +
                "modified_at: ${modified_at},\n" +
                "project_id: ${project_id},\n" +
                "project_name: ${project_name},\n" +
                "status: ${status},\n" +
                "session_id: ${session_id},\n" +
                "doc_id: ${doc_id},\n" +
                "\n}"
    }

    fun toItemCard(): String {
        return "Backlog => {\n" +
                "doc_id: ${doc_id},\n" +
                "project_name: ${project_name},\n" +
                "project_id: ${project_id},\n" +
                "status: ${status},\n" +
                "\n}"
    }
}