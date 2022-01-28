package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.service.response.BacklogResponse
import com.example.scrumpokerapp.service.response.BacklogStoryResponse

class BacklogViewModel(
    val apiController: ApiController
) : ViewModel() {

    val selectedItemDocId: MutableLiveData<String?> = MutableLiveData()
    val backlogListData: MutableLiveData<BacklogResponse?> = apiController.backlogResponseMutableLiveData
    val backlogStoryListData : MutableLiveData<BacklogStoryResponse?> = apiController.backlogStoriesResponseMutableLiveData

    fun getBacklogList(){
        apiController.getAllBacklogs()
    }

    fun loadStoryList(collection: String, doc_id: String){
        when(collection){
            "backlog-story" -> apiController.getAllStoriesFromBacklog(doc_id)
        }
    }

}