package com.example.scrumpokerapp.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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
                firebaseMutableLiveData.postValue(mAuth.currentUser)
                Toast.makeText(application,"Bienvenido!", Toast.LENGTH_SHORT).show()
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
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
                    Log.w(TAG, "login:failure", task.exception)
                    Toast.makeText(application,"Error de usuario y contraseña", Toast.LENGTH_SHORT).show()
                }
            }
    }

}