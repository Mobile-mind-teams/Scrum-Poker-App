package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel(val apiController: ApiController, val application: Application) : ViewModel() {

    val authenticationRepository: AuthenticationRepository = AuthenticationRepository(application)
    val userData: MutableLiveData<FirebaseUser> = authenticationRepository.firebaseMutableLiveData

    fun register(usersRegisterRequest: UsersRegisterRequest) {
        authenticationRepository.register(usersRegisterRequest)
    }

    fun postUser(usersRegisterRequest: UsersRegisterRequest){
        usersRegisterRequest.uid = userData.value!!.uid
        apiController.postUsersApi(usersRegisterRequest)
    }

}