package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.service.response.ProjectResponse
import com.example.scrumpokerapp.service.response.UsersResponse

class CreateSessionViewModel(
    val apiController: ApiController
) : ViewModel() {
    val userListMutableLiveData : MutableLiveData<UsersResponse?> = apiController.userResponseMutableLiveData
    val projectListMutableLiveData : MutableLiveData<ProjectResponse?> = apiController.projectResponseMutableLiveData

    var projectId : String? = null
    var emailAddresseUserList : String = ""
    var emailUserList : ArrayList<String> = arrayListOf()

    fun loadAvailableUsersList(){
        apiController.getAllAvailableUsers()
    }

    fun loadUnassignedProjects(){
        apiController.getAllUnassignedProjects()
    }

    fun emailUserListToString() : String{
        emailAddresseUserList = ""
        for (email : String in emailUserList){
            emailAddresseUserList = email + ";" + emailAddresseUserList
        }

        return emailAddresseUserList
    }
}