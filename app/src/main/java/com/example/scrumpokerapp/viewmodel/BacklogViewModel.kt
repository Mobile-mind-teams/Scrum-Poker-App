package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.service.response.BacklogResponse

class BacklogViewModel(
    val apiController: ApiController
) : ViewModel() {

    val selectedItemDocId: MutableLiveData<String?> = MutableLiveData()
    val backlogListData: MutableLiveData<BacklogResponse?> = apiController.backlogResponseMutableLiveData

    fun getBacklogList(){
        apiController.getAllBacklogs()
    }

}