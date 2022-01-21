package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class LogInViewModel(val application: Application) : ViewModel() {

    val authenticationRepository: AuthenticationRepository = AuthenticationRepository(application)
    val userData: MutableLiveData<FirebaseUser> = authenticationRepository.firebaseMutableLiveData

    fun login(email: String, password: String){
        authenticationRepository.login(email, password)
    }

}