package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.repository.AuthenticationRepository
import com.example.scrumpokerapp.service.response.SessionResponse
import com.example.scrumpokerapp.service.response.SessionsHistoryResponse
import com.example.scrumpokerapp.service.response.UsersResponse
import com.google.firebase.auth.FirebaseUser

class HomeViewModel (
    val apiController: ApiController,
    val application: Application
    ) : ViewModel() {

    val authenticationRepository: AuthenticationRepository = AuthenticationRepository(application)
    val userData: MutableLiveData<FirebaseUser> = authenticationRepository.firebaseMutableLiveData
    val userLoggedData : MutableLiveData<UsersResponse?> = apiController.userResponseMutableLiveData
    val historySessionListData : MutableLiveData<SessionsHistoryResponse?> = apiController.sessionHistoryResponseMutableLiveData
    val sesionListData: MutableLiveData<SessionResponse?> = apiController.sessionResponseMutableLiveData

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
        apiController.getAllUserSessions(uid)
    }

    fun getAllSessions(){
        apiController.getAllSessions()
    }

    fun getSessionList(){
        if (UserProfile.isProductOwner()){
            getAllSessions()
        } else {
            getAllUserSessions(
                UserProfile.uid.toString()
            )
        }
    }

}