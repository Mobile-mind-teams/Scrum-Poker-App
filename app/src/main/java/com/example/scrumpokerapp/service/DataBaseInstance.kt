package com.example.scrumpokerapp.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataBaseInstance {

    lateinit var firestoreInstance: FirebaseFirestore
    lateinit var instanceTypeTag: String

    constructor(){}

    constructor(instanceType: FirebaseFirestore, tag: String){
        this.firestoreInstance = instanceType
        this.instanceTypeTag = tag
    }

    fun initDataBaseInstance(tag: String): DataBaseInstance{
        when(tag){
            "Firestore" -> return DataBaseInstance(Firebase.firestore, tag)
            else -> return DataBaseInstance()
        }
    }
}