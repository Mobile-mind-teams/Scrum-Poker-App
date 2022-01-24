package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.controller.ApiSessionController

class HomeViewModelFactory(
    private val apiController: ApiController,
    private val apiSessionController: ApiSessionController,
    private val application: Application
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(apiController,apiSessionController,application) as T
    }

}