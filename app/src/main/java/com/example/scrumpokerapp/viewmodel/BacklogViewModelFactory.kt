package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scrumpokerapp.controller.ApiController

class BacklogViewModelFactory(
    private val apiController: ApiController
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BacklogViewModel(apiController) as T
    }
}