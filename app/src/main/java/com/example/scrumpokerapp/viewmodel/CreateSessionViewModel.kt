package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.controller.ApiSessionController

class CreateSessionViewModel(
    val apiController: ApiController,
    val apiSessionController: ApiSessionController,
    val application: Application
) : ViewModel() {

}