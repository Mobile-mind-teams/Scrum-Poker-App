package com.example.scrumpokerapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.service.response.CardsResponse

class SessionViewModel(val apiController: ApiController, val application: Application) : ViewModel() {

    val deckData: MutableLiveData<CardsResponse?> = apiController.cardsResponseMutableLiveData

    fun getDeckByUserRole(role_string: String) {
        apiController.getDeckByUserRole(role_string)
    }

}