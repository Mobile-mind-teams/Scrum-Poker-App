package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class Email {
    var project_name : String? = null
    var session_id : String? = null
    @SerializedName("from") var from : String = "charliebarttlet@icloud.com"
    @SerializedName("to") var to : String? = null
    @SerializedName("subject") var subject :
            String = "Sesion de Scrum Poker ${session_id}"
    @SerializedName("html") var html :
            String = "Saludos, <br><br>" +
            " Has sido elegid@ para participar en la asignacion de tiempos" +
            " para el proyecto ${project_name}. <br><br> " +
            "La reunion esta por empezar, <br><br> " +
            "Puedes ir a la app pulsando <a href=\\\"https://www.scrum-poker.com/open\\\"> aqui. </a>\""

    constructor(){}

    constructor(project_name: String, session_id: String, to: String){
        this.project_name = project_name
        this.session_id = session_id
        this.to = to
    }

    override fun toString(): String {
        return "Email => {\n" +
                "from: ${from},\n" +
                "to: ${to},\n" +
                "subject: ${subject},\n" +
                "html: ${html},\n" +
                "\n}"
    }
}