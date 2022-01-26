package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController

class CreateSessionViewModel(
    val apiController: ApiController,
    val application: Application
) : ViewModel() {

}