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

    constructor(project_name: String, project_id: String, status: String, doc_id : String){
        this.project_name = project_name
        this.project_id = project_id
        this.status = status
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