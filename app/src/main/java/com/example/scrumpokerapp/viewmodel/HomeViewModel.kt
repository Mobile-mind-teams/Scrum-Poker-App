package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.service.response.SessionResponse
import com.example.scrumpokerapp.utils.ProjectUtils

class HomeViewModel (
    val apiController: ApiController,
    val application: Application
    ) : ViewModel() {

    val sesionListData: MutableLiveData<SessionResponse?> = apiController.sessionResponseMutableLiveData

    fun getAllUserSessions(uid: String){
        apiController.getAllUserSessions(uid)
    }

    fun getAllSessions(po_id: String){
        apiController.getAllSessions(po_id)
    }

    fun getSessionList(userProfile: UserProfile?){
        if (ProjectUtils().isProjectOwner(userProfile?.role)){
            getAllSessions(
                userProfile?.uid.toString()
            )
        } else {
            getAllUserSessions(
                userProfile?.email.toString()
            )
        }
    }

}