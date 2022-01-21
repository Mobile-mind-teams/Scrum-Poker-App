package com.example.scrumpokerapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.example.scrumpokerapp.service.request.UsersRegisterRequest
import com.example.scrumpokerapp.service.response.AddUserResponse
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class SignUpViewModel(val apiController: ApiController, val application: Application) : ViewModel() {

    val authenticationRepository: AuthenticationRepository = AuthenticationRepository(application)
    val userData: MutableLiveData<FirebaseUser> = authenticationRepository.firebaseMutableLiveData
    val insertUserResponseData : MutableLiveData<AddUserResponse?> = apiController.createUserMutableLiveData

    fun register(usersRegisterRequest: UsersRegisterRequest) {
        authenticationRepository.register(usersRegisterRequest)
    }

    fun postUser(usersRegisterRequest: UsersRegisterRequest){
        usersRegisterRequest.uid = userData.value!!.uid
        apiController.postUsersApi(usersRegisterRequest)
    }

}