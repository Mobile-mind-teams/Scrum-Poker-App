package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.model.*
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.service.response.*
import com.example.scrumpokerapp.utils.ProjectUtils

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
    val projectStoryListMutableLiveData : MutableLiveData<ProjectStoryResponse?> = apiController.projectStoryListMutableLiveData
    val sessionStoryListMutableLiveData : MutableLiveData<SessionStoriesResponse?> = apiController.storySessionListMutableLiveData

    var projectItem : Project = Project()
    var emailAddresseUserList : String = ""
    var selectedUsers : ArrayList<User> = arrayListOf()
    val selectedUsersEmailList: ArrayList<String> = arrayListOf()
    var usersToUpdate: Int = 0
    var storyList: List<SessionStory> = listOf()

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

    fun updateAdminStatus(userProfile: UserProfile?){
        apiController.updateUser(
            User(
                userProfile?.email.toString(),
                "unavailable"
            ),
            userProfile?.doc_id.toString(),
            userProfile?.role
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

    fun getNewSessionID(userProfile: UserProfile?){
        apiController.getJustCreatedSession(
            userProfile?.uid.toString(),
            "new"
        )
    }

    fun updateProjectStatus(project: Project) {
        apiController.updateProject(
            project
        )
    }

    fun updateUserProfile(userProfile: UserProfile?) : UserProfile{
        userProfile?.status = "unavailable"
        return userProfile!!
    }

    fun getProjectStories(project_id: String){
        apiController.getAllProjectStories(project_id)
    }

    fun addStoriesToSession(storyList: List<SessionStory>, session_id: String){
        apiController.addStoriesToSession(storyList, session_id)
    }
}