package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.controller.SnapshotRepository
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.service.response.CardsResponse
import com.example.scrumpokerapp.service.response.SessionStoriesResponse
import com.example.scrumpokerapp.service.response.TableCardResponse

class SessionViewModel(val apiController: ApiController) : ViewModel() {

    val snapshotRepository: SnapshotRepository = SnapshotRepository()
    val sessionData: MutableLiveData<Session?> = snapshotRepository.sessionSnapshotMutableLiveData
    val deckData: MutableLiveData<CardsResponse?> = apiController.cardsResponseMutableLiveData
    val sessionStoryList: MutableLiveData<SessionStoriesResponse?> = apiController.sessionStoriesResponseMutableLiveData
    val tableCardSent: MutableLiveData<TableCardResponse?> = apiController.tableCardResponseMutableLiveData

    fun getDeckByUserRole(role_string: String) {
        apiController.getDeckByUserRole(role_string)
    }

    fun getSessionStories(session_id: String){
        apiController.getStoryListBySessionID(session_id)
    }

    fun setTableCard(card: UserCard, session_id: String){
        apiController.setTableCard(card,session_id)
    }

    fun getSessionSnapshot(){
        snapshotRepository.getFirebaseSessionSnapshot()
    }

}