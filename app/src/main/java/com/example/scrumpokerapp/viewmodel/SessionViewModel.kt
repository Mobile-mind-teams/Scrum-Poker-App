package com.example.scrumpokerapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.StoryListItemBinding
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
    val currentStory: MutableLiveData<SessionStory?> = snapshotRepository.currentStorySnapshotData
    val isAllStoryList : MutableLiveData<Boolean> = MutableLiveData()
    val sessionSnapshotData : MutableLiveData<Session?> = snapshotRepository.sessionSnapshotMutableLiveData
    val tableData : MutableLiveData<TableCardResponse?> = snapshotRepository.pokerTableSnapshotData
    val clearTableData : MutableLiveData<TableCardResponse?> = snapshotRepository.clearPokerTableSnapshotData

    lateinit var sessionObject : Session
    var storyListToWork : ArrayList<SessionStory> = arrayListOf()
    var cardsOnTable : ArrayList<UserCard> = arrayListOf()

    fun getDeckByUserRole(role_string: String) {
        apiController.getDeckByUserRole(role_string)
    }

    fun getSessionStories(session_id: String){
        apiController.getStoryListBySessionID(session_id)
    }

    fun setTableCard(card: UserCard, session_id: String){
        apiController.setTableCard(card,session_id)
    }

    fun getSessionSnapshot(session_id: String){
        snapshotRepository.getFirebaseSessionSnapshot(session_id)
    }

    fun getCurrentStorySessionSanpshot(session_id: String){
        snapshotRepository.getFirebaseCurrentStorySnapshot(session_id)
    }

    fun getPokerTableSnapshot(session_id: String, story_id: String){
        snapshotRepository.getFirebasePokerTableSnapshot(session_id, story_id)
    }

    fun getClearPokerTableSnapshot(session_id: String){
        snapshotRepository.getFirebaseClearPokerTableSnapshot(session_id)
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

    fun launchStory(session_id: String, current_story: SessionStory?){
        var story : SessionStory = popStory(storyListToWork)
        if (story.doc_id != null && current_story == null){
            apiController.updateStoryFrom(story, session_id, "session")
        } else if (story.doc_id != null && current_story != null) {
            current_story.visibility = false
            apiController.updateStoryFrom(current_story, session_id, "session")
            apiController.updateStoryFrom(story, session_id, "session")
        } else if (current_story != null) {
            apiController.updateStoryFrom(current_story, session_id, "session")
            isAllStoryList.postValue(true)
        }
    }

    fun goToBreak(session: Session){
        session.status = "onBreak"
        currentStory.value?.visibility = false
        apiController.updateStoryFrom(currentStory.value!!, session.session_id!!, "session")
        apiController.updateSession(session)
    }

    fun updateStoryValue(session_id: String, value: Double){
        currentStory.value?.weight = value
        currentStory.value?.agreed_status = true
        apiController.updateStoryFrom(currentStory.value!!, session_id, "session")
    }

    fun addStoryNote(session_id: String, note: String){
        currentStory.value?.note = note
        currentStory.value?.agreed_status = true
        apiController.updateStoryFrom(currentStory.value!!, session_id, "session")
    }

    private fun popStory(storyListToWork: ArrayList<SessionStory>): SessionStory {
        var sessionStory : SessionStory
        if (storyListToWork.size > 0){
            sessionStory = storyListToWork.get(0)
            sessionStory.visibility = true
            sessionStory.read_status = true
            storyListToWork.removeAt(0)
        } else {
            sessionStory = SessionStory()
        }
        return sessionStory
    }

    fun clearTable(session_id: String, story_id: String) {
        apiController.clearTable(session_id, story_id)
    }

    fun flipCards(session_id: String, table_card_list: List<UserCard>){
        apiController.flipCards(session_id, table_card_list)
    }

    fun repeatTurn(session_id: String, story_id: String){
        clearTable(session_id, story_id)
    }

    fun nextTurn(session_id: String, value: Double) {
        currentStory.value?.agreed_status = true
        currentStory.value?.weight = value
        apiController.updateStoryFrom(currentStory.value!!, session_id, "session")
    }

    fun loadTableCards(cards: List<UserCard>) {
        cardsOnTable.clear()
        for(card in cards){
            cardsOnTable.add(card)
        }
    }

    fun finishSession(sessionId: String) {
        Log.i("Nothing To Show: ","FINISH SESSION ")
    }

    fun loadEndoOfListItem(currentStoryLayout: StoryListItemBinding) {
        currentStoryLayout.storyTitle.setText("END OF LIST")
        currentStoryLayout.storyDescription.setText("END OF LIST")
        currentStoryLayout.storyWeight.setText("0")
    }

    fun loadStoryItem(currentStoryLayout: StoryListItemBinding, sessionStory: SessionStory) {
        currentStoryLayout.storyTitle.setText(sessionStory.title)
        currentStoryLayout.storyDescription.setText(sessionStory.description)
        currentStoryLayout.storyWeight.setText(sessionStory.weight.toString())
    }

}