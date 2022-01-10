package com.example.scrumpokerapp.repository

import android.app.Application
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.firebase.FirebaseKeysValues
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthenticationRepository {
    var application: Application
    var firebaseMutableLiveData: MutableLiveData<FirebaseUser>
    var mAuth: FirebaseAuth

    constructor(application: Application) {
        this.application = application
        firebaseMutableLiveData = MutableLiveData()
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null){
            firebaseMutableLiveData.postValue(mAuth.currentUser)
        }
    }

    fun register(usersRegisterRequest: UsersRegisterRequest){
        mAuth.createUserWithEmailAndPassword(usersRegisterRequest.email, usersRegisterRequest.password)
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                usersRegisterRequest.uid = mAuth.currentUser?.uid!!.toString()
                saveFirestoreEntry(usersRegisterRequest)
                firebaseMutableLiveData.postValue(mAuth.currentUser)
            } else {
                Toast.makeText(application,"Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseMutableLiveData.postValue(mAuth.currentUser)
                } else {
                    Toast.makeText(application,"Error de usuario y contraseña", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun saveFirestoreEntry(usersRegisterRequest: UsersRegisterRequest) {
        val db = Firebase.firestore

        db.collection(FirebaseKeysValues().USER_COLLECTION_KEY)
            .add(usersRegisterRequest)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
}