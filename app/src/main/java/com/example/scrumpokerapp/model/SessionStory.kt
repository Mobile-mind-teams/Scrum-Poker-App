package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class SessionStory {
    @SerializedName("title") var title : String? = null
    @SerializedName("description") var description : String? = null
    @SerializedName("weight") var weight : Double? = null
    @SerializedName("read_status") var read_status : Boolean? = null
    @SerializedName("agreed_status") var agreed_status : Boolean? = null
    @SerializedName("visibility") var visibility : Boolean? = null
    @SerializedName("note") var note : String? = null
    @SerializedName("sid") var doc_id : String? = null

    constructor()

    constructor(title: String?, description: String?, doc_id: String?) {
        this.title = title
        this.description = description
        this.weight = 0.0
        this.read_status = false
        this.agreed_status = false
        this.visibility = true
        this.doc_id = doc_id
        this.note = ""
    }

    fun transformToJASONtxt(): String {
        return "\tSessionStory => {\n" +
                "\ttitle: ${title},\n" +
                "\tdescription: ${description},\n" +
                "\tweight: ${weight},\n" +
                "\tread_status: ${read_status},\n" +
                "\tagreed_status: ${agreed_status},\n" +
                "\tvisibility: ${visibility},\n" +
                "\tnote: ${note},\n" +
                "\tdoc_id: ${doc_id},\n" +
                "\t}"
    }
}