package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.example.scrumpokerapp.service.response.UsersResponse
import com.google.firebase.auth.FirebaseUser

class LogInViewModel(
    val application: Application,
    val apiController: ApiController
    ) : ViewModel() {

    val authenticationRepository: AuthenticationRepository = AuthenticationRepository(application)
    val userData: MutableLiveData<FirebaseUser> = authenticationRepository.firebaseMutableLiveData
    val userLoggedData: MutableLiveData<UsersResponse?> = apiController.userResponseMutableLiveData

    fun login(email: String, password: String){
        authenticationRepository.login(email, password)
    }

    fun getUserById(uid: String){
        apiController.getUserByIdApi(uid)
    }

}