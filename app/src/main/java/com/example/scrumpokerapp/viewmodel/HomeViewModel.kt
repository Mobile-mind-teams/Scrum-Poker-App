package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.controller.ApiSessionController
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.example.scrumpokerapp.service.response.SessionsHistoryResponse
import com.example.scrumpokerapp.service.response.UsersResponse
import com.google.firebase.auth.FirebaseUser

class HomeViewModel (
    val apiController: ApiController,
    val apiSessionController: ApiSessionController,
    val application: Application
    ) : ViewModel() {

    val authenticationRepository: AuthenticationRepository = AuthenticationRepository(application)
    val userData: MutableLiveData<FirebaseUser> = authenticationRepository.firebaseMutableLiveData
    val userLoggedData : MutableLiveData<UsersResponse?> = apiController.userResponseMutableLiveData
    val sessionListData : MutableLiveData<SessionsHistoryResponse?> = apiSessionController.sessionHistoryResponseMutableLiveData
    val logOutStatus: MutableLiveData<Boolean> = authenticationRepository.logoutStatusMutableLiveData

    fun getLoggedUserUid() : String {
        return userData.value?.uid.toString()
    }

    fun getUserById(uid: String){
        apiController.getUserByIdApi(uid)
    }

    fun logout(){
        authenticationRepository.logout()
    }

    fun getAllUserSessions(uid: String){
        apiSessionController.getAllUserSessions(uid)
    }

    /*fun getAllSessions(){
        apiSessionController.getAllSessions()
    }*/

    fun getCurrentUserObject() : User?{
        return userLoggedData.value?.data?.get(0)
    }

}