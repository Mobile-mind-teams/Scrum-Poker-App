package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(){
    val showBottomNavigationMenu: MutableLiveData<Boolean> = MutableLiveData()
    val loggedStatus : MutableLiveData<Boolean> = MutableLiveData()
}