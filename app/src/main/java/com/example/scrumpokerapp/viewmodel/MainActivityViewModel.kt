package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.persistance.UserProfile

class MainActivityViewModel : ViewModel(){
    val showBottomNavigationMenu: MutableLiveData<Boolean> = MutableLiveData()
    val showCreateSessionBottomNavigationMenuItem: MutableLiveData<Boolean> = MutableLiveData()
    val loggedStatus : MutableLiveData<Boolean> = MutableLiveData()
    val userData: MutableLiveData<UserProfile> = MutableLiveData()
}