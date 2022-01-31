package com.example.scrumpokerapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.model.Email
import com.example.scrumpokerapp.service.response.EmailResponse
import com.example.scrumpokerapp.service.response.ProjectResponse
import com.example.scrumpokerapp.service.response.UsersResponse

class CreateSessionViewModel(
    val apiController: ApiController
) : ViewModel() {
    val userListMutableLiveData : MutableLiveData<UsersResponse?> = apiController.userResponseMutableLiveData
    val projectListMutableLiveData : MutableLiveData<ProjectResponse?> = apiController.projectResponseMutableLiveData
    val emailMutableLiveData : MutableLiveData<EmailResponse?> = apiController.emailResponseMutableLiveData

    var projectName : String = ""
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

        if (emailUserList.size == 1) {
            return emailUserList.get(0)
        } else {
            for (email : String in emailUserList){
                emailAddresseUserList = email + ";" + emailAddresseUserList
            }
        }

        return emailAddresseUserList
    }

    fun sendEmail(email: Email){
        apiController.sendEmailApi(email)
    }
}