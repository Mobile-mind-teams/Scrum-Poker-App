package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : AndroidViewModel {
    var authenticationRepository: AuthenticationRepository
    var userData: MutableLiveData<FirebaseUser>

    constructor(application: Application) : super(application) {
        authenticationRepository = AuthenticationRepository(application)
        userData = authenticationRepository.firebaseMutableLiveData
    }

    fun register(email: String, password: String){
        authenticationRepository.register(email,password)
    }

    fun login(email: String, password: String){
        authenticationRepository.login(email, password)
    }
}