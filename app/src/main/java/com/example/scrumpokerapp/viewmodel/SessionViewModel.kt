package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.SessionStory
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.repository.SnapshotRepository
import com.example.scrumpokerapp.service.response.CardsResponse
import com.example.scrumpokerapp.service.response.SessionResponse
import com.example.scrumpokerapp.service.response.SessionStoriesResponse
import com.example.scrumpokerapp.service.response.TableCardResponse

class SessionViewModel(val apiController: ApiController) : ViewModel() {

    val snapshotRepository: SnapshotRepository = SnapshotRepository()
    val sessionData: MutableLiveData<SessionResponse?> = apiController.currentSessionResponseMutableLiveData
    val deckData: MutableLiveData<CardsResponse?> = apiController.cardsResponseMutableLiveData
    val sessionStoryList: MutableLiveData<SessionStoriesResponse?> = apiController.sessionStoriesResponseMutableLiveData
    val tableCardSent: MutableLiveData<TableCardResponse?> = apiController.tableCardResponseMutableLiveData
    val currentStory: MutableLiveData<SessionStory?> = snapshotRepository.currentStorySnapdhotData
    val updateCurrentStoryData : MutableLiveData<SessionStoriesResponse?> = apiController.currentStoryMutableLiveData
    val isAllStoryList : MutableLiveData<Boolean> = MutableLiveData()

    lateinit var sessionObject : Session
    var storyListToWork : ArrayList<SessionStory> = arrayListOf()

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

    fun getCurrentStorySessionSanpshot(session_id: String){
        snapshotRepository.getFirebaseCurrentStorySnapshot(session_id)
    }

    fun getSessionData(session_id: String){
        apiController.getSessionByID(session_id)
    }

    fun loadSessionObject(session: Session){
        this.sessionObject = session
    }

    fun loadSessionStories(storyList : List<SessionStory>){
        for (story in storyList){
            storyListToWork.add(story)
        }
    }

    fun launchStory(session_id: String){
        val story : SessionStory = popStory(storyListToWork)
        if (story.doc_id != null){
            apiController.updateStoryFrom(story, session_id, "session")
        } else {
            isAllStoryList.postValue(true)
        }
    }

    fun updateStoryValue(session_id: String, value: Double){
        currentStory.value?.weight = value
        apiController.updateStoryFrom(currentStory.value!!, session_id, "session")
    }

    private fun popStory(storyListToWork: ArrayList<SessionStory>): SessionStory {
        var sessionStory : SessionStory
        if (storyListToWork.size > 0){
            sessionStory = storyListToWork.get(0)
            sessionStory.visibility = true
            storyListToWork.removeAt(0)
        } else {
            sessionStory = SessionStory()
        }
        return sessionStory
    }

}