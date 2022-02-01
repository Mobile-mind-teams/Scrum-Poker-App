package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.model.Email
import com.example.scrumpokerapp.model.Project
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.User
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.service.response.EmailResponse
import com.example.scrumpokerapp.service.response.ProjectResponse
import com.example.scrumpokerapp.service.response.SessionResponse
import com.example.scrumpokerapp.service.response.UsersResponse

class CreateSessionViewModel(
    val apiController: ApiController
) : ViewModel() {
    val usersUpdateMutableLiveData : MutableLiveData<UsersResponse?> = apiController.userUpdateResponseMutableLiveData
    val usersListMutableLiveData : MutableLiveData<UsersResponse?> = apiController.userListResponseMutableLiveData
    val adminUpdateMutableLiveData : MutableLiveData<UsersResponse?> = apiController.adminResponseMutableLiveData
    val projectListMutableLiveData : MutableLiveData<ProjectResponse?> = apiController.projectResponseMutableLiveData
    val projectUpdateMutableLiveData : MutableLiveData<ProjectResponse?> = apiController.projectUpdateResponseMutableLiveData
    val emailMutableLiveData : MutableLiveData<EmailResponse?> = apiController.emailResponseMutableLiveData
    val newSessionMutableLiveData : MutableLiveData<SessionResponse?> = apiController.sessionResponseMutableLiveData
    val newSessionIDMutableLiveData : MutableLiveData<SessionResponse?> = apiController.newSessionResponseMutableLiveData

    var projectItem : Project = Project()
    var emailAddresseUserList : String = ""
    var selectedUsers : ArrayList<User> = arrayListOf()
    val selectedUsersEmailList: ArrayList<String> = arrayListOf()
    var usersToUpdate: Int = 0

    fun loadAvailableUsersList(){
        apiController.getAllAvailableUsers()
    }

    fun loadUnassignedProjects(){
        apiController.getAllUnassignedProjects()
    }

    fun sendEmail(email: Email){
        apiController.sendEmailApi(email)
    }

    fun createSession(session: Session){
        apiController.createNewSession(session)
    }

    fun updateAdminStatus(){
        apiController.updateUser(
            User(
                UserProfile.email,
            "unavailable"
            ),
            UserProfile.doc_id.toString(),
            UserProfile.role
        )
    }

    fun updateUsersStatus(){
        for (user in selectedUsers){
            apiController.updateUser(
                User(
                    user.email.toString(),
                    "unavailable"
                ),
                user.doc_id.toString(),
                user.role
            )
        }
    }

    fun getSelectedUsersEmailList(): List<String> {
        if (selectedUsers.size == 1) {
            selectedUsersEmailList.add(selectedUsers.get(0).email.toString())
            emailAddresseUserList = selectedUsers.get(0).email.toString()
        } else {
            for (user : User in selectedUsers){
                selectedUsersEmailList.add(user.email.toString())
                emailAddresseUserList = user.email + ";" + emailAddresseUserList
            }
        }

        usersToUpdate = selectedUsers.size
        return selectedUsersEmailList
    }

    fun getNewSessionID(){
        apiController.getJustCreatedSession(
            UserProfile.uid.toString(),
            "new"
        )
    }

    fun updateProjectStatus(project: Project) {
        apiController.updateProject(
            project
        )
    }
}