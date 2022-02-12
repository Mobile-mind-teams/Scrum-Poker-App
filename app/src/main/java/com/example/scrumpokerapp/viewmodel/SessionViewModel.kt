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

class SessionViewModel(val apiController: ApiController) : ViewModel() {

    val snapshotRepository: SnapshotRepository = SnapshotRepository()
    val sessionData: MutableLiveData<SessionResponse?> = apiController.currentSessionResponseMutableLiveData
    val deckData: MutableLiveData<CardsResponse?> = apiController.cardsResponseMutableLiveData
    val sessionStoryList: MutableLiveData<SessionStoriesResponse?> = apiController.sessionStoriesResponseMutableLiveData
    val tableCardSent: MutableLiveData<TableCardResponse?> = apiController.tableCardResponseMutableLiveData
    val currentStory: MutableLiveData<SessionStory?> = snapshotRepository.currentStorySnapshotData
    val isAllStoryList : MutableLiveData<Boolean> = MutableLiveData()
    val goToHomeData : MutableLiveData<Boolean> = MutableLiveData()
    val sessionSnapshotData : MutableLiveData<Session?> = snapshotRepository.sessionSnapshotMutableLiveData
    val tableData : MutableLiveData<TableCardResponse?> = snapshotRepository.pokerTableSnapshotData
    val clearTableData : MutableLiveData<TableCardResponse?> = snapshotRepository.clearPokerTableSnapshotData
    val userCardInfo : MutableLiveData<UserCard?> = MutableLiveData()
    val currentCardSent : MutableLiveData<UserCard?> = snapshotRepository.currentCardSentSnapshotData
    val backlogSnapshotData: MutableLiveData<Backlog?> = snapshotRepository.backlogCreatedSnapshotData
    val backlogStoryListData: MutableLiveData<BacklogStoryResponse?> = apiController.backlogStoriesResponseMutableLiveData
    val projectUpdateMutableLiveData : MutableLiveData<ProjectResponse?> = apiController.projectUpdateResponseMutableLiveData
    val adminUpdateMutableLiveData : MutableLiveData<UsersResponse?> = apiController.adminResponseMutableLiveData
    val userUpdateMutableLiveData : MutableLiveData<UsersResponse?> = apiController.userUpdateResponseMutableLiveData

    lateinit var currentUser : UserProfile
    lateinit var sessionObject : Session
    var storyListToWork : ArrayList<SessionStory> = arrayListOf()
    var cardsOnTable : ArrayList<UserCard> = arrayListOf()
    var areAllSessionStoriesWithWeightValue : Boolean = true

    fun getDeckByUserRole(role_string: String) {
        apiController.getDeckByUserRole(role_string)
    }

    fun getSessionStories(session_id: String){
        apiController.getStoryListBySessionID(session_id)
    }

    fun setTableCard(card: UserCard, session_id: String){
        if (validaUserCard()){
            card.doc_id = currentCardSent.value?.doc_id
            apiController.updateTableCard(session_id, card)
        } else {
            apiController.setTableCard(card,session_id)
        }
    }

    private fun validaUserCard(): Boolean {
        return currentCardSent.value != null
    }

    fun getSessionSnapshot(session_id: String){
        snapshotRepository.getFirebaseSessionSnapshot(session_id)
    }

    fun getBacklogSnapshot(session_id: String){
        snapshotRepository.getFirebaseCreatedBacklogSnapshot(session_id)
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

    fun getCurrentCardSentData(session_id: String){
        snapshotRepository.getFirebaseCurrentCardSentSnapshot(session_id, currentUser.uid!!)
    }

    fun getSessionData(session_id: String){
        apiController.getSessionByID(session_id)
    }

    fun loadUserProfileObject(user: UserProfile) {
        this.currentUser = user
    }

    fun loadSessionObject(session: Session){
        this.sessionObject = session
    }

    fun loadSessionStories(storyList : List<SessionStory>){
        for (story in storyList){
            if (story.weight == 0.0){
                areAllSessionStoriesWithWeightValue = false
            }
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
        currentStory.value?.visibility = false
        popStory(storyListToWork)
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

    fun loadTableCards(cards: List<UserCard>) {
        cardsOnTable.clear()
        for(card in cards){
            cardsOnTable.add(card)
        }
    }

    fun finishSession(session: Session) {
        session.status = "finished"
        session.finished_at = ProjectUtils().generateTimeStamp()
        apiController.updateSession(session)
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

    fun executeAction(userCard: UserCard, isProjectOwner: Boolean, isTableCard: Boolean) {
        if (isProjectOwner && isTableCard){
            when(userCard.action){
                "setWeight" -> updateStoryValue(sessionObject.session_id!!, userCard.value!!)
                "goToBreak" -> goToBreak(sessionObject)
                "addNote" -> addStoryNote(sessionObject.session_id!!, "Nota escrita por usuario")
            }
        } else if (!isProjectOwner && isTableCard){
            if (userCard.user_id == currentUser.uid){
                showCardInfo(userCard)
            } else {
                userCardInfo.postValue(null)
            }
        } else {
            when(userCard.action){
                "launchStory" -> launchStory(sessionObject.session_id!!, currentStory.value)
                "repeatTurn" -> repeatTurn(sessionObject.session_id!!, currentStory.value?.doc_id!!)
                "flipCards" -> flipCards(sessionObject.session_id!!, cardsOnTable)
                "finishSession" -> finishSession(sessionObject)
                "goToHome" -> goToHome()
                else -> setTableCard(userCard, sessionObject.session_id!!)
            }
        }
    }

    private fun goToHome() {
        goToHomeData.postValue(true)
    }

    private fun showCardInfo(userCard: UserCard) {
        userCardInfo.postValue(userCard)
    }

    fun createBacklog(sessionObject: Session) {
        apiController.createBacklogAPI(Backlog(
            ProjectUtils().generateTimeStamp(),
            "-",
            sessionObject.project_id,
            sessionObject.project_name,
            getBacklogStatus(),
            sessionObject.session_id
        ))
    }

    fun addStoriesToBacklog(storyList: List<SessionStory>) {
        if (backlogStoryListData.value == null){
            apiController.addStoriesToBacklog(storyList, backlogSnapshotData.value?.doc_id!!)
        }
    }

    fun getStoriesForBacklog(session_id: String) {
        apiController.getStoriesForBacklog(session_id)
    }

    fun updateProjectStatus(project: Project) {
        apiController.updateProject(
            project
        )
    }

    fun getProjectStatus(): String {
        return if (areAllSessionStoriesWithWeightValue) "assignedToBacklog" else "unassigned"
    }

    fun getBacklogStatus(): String {
        return if (areAllSessionStoriesWithWeightValue) "complete" else "incomplete"
    }

    fun updateAdminStatus(userProfile: UserProfile?){
        apiController.updateUser(
            User(
                userProfile?.email!!,
                "available"
            ),
            userProfile.doc_id.toString(),
            userProfile.role
        )
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

    fun updateUserProfile(userProfile: UserProfile?) : UserProfile{
        userProfile?.status = "available"
        return userProfile!!
    }
}