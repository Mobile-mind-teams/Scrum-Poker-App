package com.example.scrumpokerapp.model

import com.google.gson.annotations.SerializedName

class Email {
    @SerializedName("from") val from : String = "charliebarttlet@icloud.com"
    @SerializedName("to") var to : String? = null
    @SerializedName("subject") var subject : String = ""
    @SerializedName("html") var html : String = ""

    constructor(){}

    constructor(project_name: String, to: String){
        this.subject = "Sesion de Scrum Poker ${project_name}"
        this.to = to
        this.html = "Saludos, <br><br>" +
                " Has sido elegid@ para participar en la asignacion de tiempos por historia" +
                " para el proyecto <strong>${project_name}</strong>. <br><br> " +
                "La reunion esta por empezar, <br><br> " +
                "Puedes ir a la app seleccionando el enlace y pulsando abrir <br><br> " +
                "<strong>https://www.scrum-poker.com/open</strong>"
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