package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.persistance.UserProfile

class MainActivityViewModel : ViewModel(){
    val showBottomNavigationMenu: MutableLiveData<Boolean> = MutableLiveData()
    val showCreateSessionBottomNavigationMenuItem: MutableLiveData<Boolean> = MutableLiveData()
    val loggedStatus : MutableLiveData<Boolean> = MutableLiveData()
    val userData: MutableLiveData<UserProfile> = MutableLiveData()

    fun getUserProfile() : UserProfile {
        return userData.value!!
    }

    fun isProjectOwner() : Boolean {
        return userData.value?.role == 1
    }

    fun isAvailable() : Boolean{
        return userData.value?.status == "available"
    }

    fun showCreateSession(): Boolean {
        return isProjectOwner() && isAvailable()
    }
}