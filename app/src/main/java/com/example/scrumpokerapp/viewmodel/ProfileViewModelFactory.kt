package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scrumpokerapp.controller.ApiController

class ProfileViewModelFactory(
    private val application: Application
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(application) as T
    }

}