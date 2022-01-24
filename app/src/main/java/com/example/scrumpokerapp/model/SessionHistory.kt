package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class SessionHistory {
    @SerializedName("admin_id") var admin_id : String? = null
    @SerializedName("pid") var pid : String? = null
    @SerializedName("sid") var sid : String? = null
    @SerializedName("status") var status : String? = null
    @SerializedName("time_stamp") var time_stamp : String? = null
    @SerializedName("uid") var uid : String? = null
    @SerializedName("project_name") var project_name : String? = null

    override fun toString(): String {
        return "Session => {\n" +
                "admin_id: ${admin_id},\n" +
                "pid: ${pid},\n" +
                "sid: ${sid},\n" +
                "status: ${status},\n" +
                "time_stamp: ${time_stamp},\n" +
                "project_name: ${project_name},\n" +
                "uid: ${uid}," +
                "\n}"
    }

    fun toItemCard(): String {
        return "Session => {\n" +
                "status: ${status},\n" +
                "project_name: ${project_name},\n" +
                "pid: ${pid},\n" +
                "sid: ${sid},\n" +
                "\n}"
    }
}