package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scrumpokerapp.controller.ApiController

class SessionViewModelFactory (
    private val apiController: ApiController,
    private val isAdmin: Boolean
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(isAdmin) {
            AdminSessionViewModel(apiController) as T
        } else {
            UserSessionViewModel(apiController) as T
        }
    }

}