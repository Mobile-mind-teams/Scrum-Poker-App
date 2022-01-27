package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scrumpokerapp.controller.ApiController

class LogInViewModelFactory (
    private val application: Application,
    private val apiController: ApiController
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LogInViewModel(application, apiController) as T
    }

}