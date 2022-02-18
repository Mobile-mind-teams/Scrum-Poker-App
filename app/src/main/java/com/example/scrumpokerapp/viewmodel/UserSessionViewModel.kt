package com.example.scrumpokerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.databinding.StoryListItemBinding
import com.example.scrumpokerapp.model.*
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.repository.SnapshotRepository
import com.example.scrumpokerapp.service.response.*
import com.example.scrumpokerapp.utils.ProjectUtils

class UserSessionViewModel (val apiController: ApiController) : ViewModel() {

    val snapshotRepository: SnapshotRepository = SnapshotRepository()
    val deckData: MutableLiveData<CardsResponse?> = apiController.cardsResponseMutableLiveData
    val currentStory: MutableLiveData<SessionStory?> = snapshotRepository.currentStorySnapshotData
    val goToHomeData : MutableLiveData<Boolean> = MutableLiveData()
    val sessionSnapshotData : MutableLiveData<Session?> = snapshotRepository.sessionSnapshotMutableLiveData
    val userCardInfo : MutableLiveData<UserCard?> = MutableLiveData()
    val updatedStorySnapshot: MutableLiveData<SessionStory?> = snapshotRepository.updatedStorySnapshotData
    val currentStorySnapshot: MutableLiveData<SessionStory?> = snapshotRepository.currentStorySnapshotData
    val tableCardListSnapshot: MutableLiveData<TableCardResponse?> = snapshotRepository.pokerTableSnapshotData
    val userLiveData: MutableLiveData<UsersResponse?> = apiController.userUpdateResponseMutableLiveData

    var cardsOnTable : ArrayList<UserCard> = arrayListOf()
    var canSend: Boolean = true

    fun getDeckByUserRole(role_string: String) {
        apiController.getDeckByUserRole(role_string)
    }

    fun getUpdatedStorySnapshot(session_id: String){
        snapshotRepository.getFirebaseUpdatedStorySnapshot(session_id)
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

    fun clearTableCards(){
        cardsOnTable.clear()
    }

    fun loadStoryStandByListItem(currentStoryLayout: StoryListItemBinding) {
        currentStoryLayout.storyTitle.setText("Waiting...")
        currentStoryLayout.storyDescription.setText("Waiting...")
        currentStoryLayout.storyWeight.setText("0")
    }

    fun loadTableCards(cards: List<UserCard>) {
        cardsOnTable.clear()
        for(card in cards){
            cardsOnTable.add(card)
        }
    }

    fun loadStoryItem(currentStoryLayout: StoryListItemBinding, sessionStory: SessionStory) {
        currentStoryLayout.storyTitle.setText(sessionStory.title)
        currentStoryLayout.storyDescription.setText(sessionStory.description)
        currentStoryLayout.storyWeight.setText(sessionStory.weight.toString())
    }

    fun setTableCard(card: UserCard, session_id: String){
        if (canSend){
            canSend = false
            apiController.setTableCard(card,session_id)
        }
    }

    fun executeAction(session: Session, userCard: UserCard, isTableCard: Boolean, userProfile: UserProfile?) {
        if (isTableCard){
            if (userCard.user_id == userProfile?.uid!!){
                showCardInfo(userCard)
            } else {
                userCardInfo.postValue(null)
            }
        } else if(!isTableCard) {
            setTableCard(userCard, session.session_id!!)
        }
    }

    private fun showCardInfo(userCard: UserCard) {
        userCardInfo.postValue(userCard)
    }

    fun updateUserStatus(userProfile: UserProfile?){
        apiController.updateUser(
            User(
                userProfile?.email!!,
                "available"
            ),
            userProfile.doc_id.toString(),
            userProfile.role
        )
    }
}