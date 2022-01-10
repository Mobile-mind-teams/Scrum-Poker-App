package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : AndroidViewModel {
    var authenticationRepository: AuthenticationRepository
    var userData: MutableLiveData<FirebaseUser>

    constructor(application: Application) : super(application) {
        authenticationRepository = AuthenticationRepository(application)
        userData = authenticationRepository.firebaseMutableLiveData
    }

    fun register(usersRegisterRequest: UsersRegisterRequest){
        authenticationRepository.register(usersRegisterRequest)
    }

    fun login(email: String, password: String){
        authenticationRepository.login(email, password)
    }
}