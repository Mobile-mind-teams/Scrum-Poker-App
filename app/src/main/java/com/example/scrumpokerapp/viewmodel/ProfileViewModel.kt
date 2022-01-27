package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.example.scrumpokerapp.service.response.UsersResponse
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(
    val application: Application
) : ViewModel() {

    val authenticationRepository: AuthenticationRepository = AuthenticationRepository(application)
    val sessionStatus: MutableLiveData<Boolean> = authenticationRepository.logoutStatusMutableLiveData

    fun logOut(){
        authenticationRepository.logout()
    }

}