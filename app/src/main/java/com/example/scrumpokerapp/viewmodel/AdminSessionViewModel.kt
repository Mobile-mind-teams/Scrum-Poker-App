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

class AdminSessionViewModel (val apiController: ApiController) : ViewModel() {

    val snapshotRepository: SnapshotRepository = SnapshotRepository()
    val deckData: MutableLiveData<CardsResponse?> = apiController.cardsResponseMutableLiveData
    val sessionStoryList: MutableLiveData<SessionStoriesResponse?> = apiController.sessionStoriesResponseMutableLiveData
    val backlogStoryList: MutableLiveData<SessionStoriesResponse?> = apiController.sessionStoriesForBacklogResponseMutableLiveData
    val currentStorySnapshot: MutableLiveData<SessionStory?> = snapshotRepository.currentStorySnapshotData
    val updatedStorySnapshot: MutableLiveData<SessionStory?> = snapshotRepository.updatedStorySnapshotData
    val isAllStoryList : MutableLiveData<Boolean> = MutableLiveData()
    val goToHomeData : MutableLiveData<Boolean> = MutableLiveData()
    val sessionSnapshotData : MutableLiveData<Session?> = snapshotRepository.sessionSnapshotMutableLiveData
    val backlogSnapshotData: MutableLiveData<Backlog?> = snapshotRepository.backlogCreatedSnapshotData
    val tableCardListSnapshot: MutableLiveData<TableCardResponse?> = snapshotRepository.pokerTableSnapshotData
    val projectLiveData: MutableLiveData<ProjectResponse?> = apiController.projectUpdateResponseMutableLiveData
    val adminLiveData: MutableLiveData<UsersResponse?> = apiController.adminResponseMutableLiveData
    val showStoryListRecycler: MutableLiveData<Boolean> = MutableLiveData()

    var storyListToWork : ArrayList<SessionStory> = arrayListOf()
    var cardsOnTable : ArrayList<UserCard> = arrayListOf()
    var areAllSessionStoriesWithWeightValue : Boolean = true
    var unlockLaunchStory: Boolean = true
    var canSend: Boolean = true
    var backlogID = ""

    fun getDeckByUserRole(role_string: String) {
        apiController.getDeckByUserRole(role_string)
    }

    fun getSessionStories(session_id: String){
        apiController.getStoryListBySessionID(session_id)
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

    fun getUpdatedStorySnapshot(session_id: String){
        snapshotRepository.getFirebaseUpdatedStorySnapshot(session_id)
    }

    fun getPokerTableSnapshot(session_id: String, story_id: String){
        snapshotRepository.getFirebasePokerTableSnapshot(session_id, story_id)
    }

    fun getBacklogStoryListSnapshot(backlog_id: String){
        snapshotRepository.getFirebaseBacklogStoryListSnapshot(backlog_id)
    }

    fun loadSessionStories(storyList : List<SessionStory>){
        storyListToWork.clear()
        for (story in storyList){
            if (story.weight == 0.0){
                areAllSessionStoriesWithWeightValue = false
            }
            storyListToWork.add(story)
        }
    }

    fun launchStory(story: SessionStory, session_id: String){
        story.visibility = true
        story.read_status = true
        apiController.updateStoryFrom(story, session_id, "session")
    }

    fun goToBreak(session: Session){
        session.status = "onBreak"
        currentStorySnapshot.value?.visibility = false
        apiController.updateStoryFrom(currentStorySnapshot.value!!, session.session_id.toString(), "session")
        apiController.updateSession(session)
    }

    fun updateStoryValue(session_id: String, value: Double){
        currentStorySnapshot.value?.weight = value
        currentStorySnapshot.value?.agreed_status = true
        currentStorySnapshot.value?.visibility = false
        apiController.updateStoryFrom(currentStorySnapshot.value!!, session_id, "session")
    }

    fun addStoryNote(session_id: String, note: String){
        currentStorySnapshot.value?.note = note
        currentStorySnapshot.value?.agreed_status = true
        apiController.updateStoryFrom(currentStorySnapshot.value!!, session_id, "session")
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

    fun clearTableCards(){
        cardsOnTable.clear()
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

    fun loadStoryStandByListItem(currentStoryLayout: StoryListItemBinding) {
        currentStoryLayout.storyTitle.setText("Waiting...")
        currentStoryLayout.storyDescription.setText("Waiting...")
        currentStoryLayout.storyWeight.setText("0")
    }

    fun loadStoryItem(currentStoryLayout: StoryListItemBinding, sessionStory: SessionStory) {
        currentStoryLayout.storyTitle.setText(sessionStory.title)
        currentStoryLayout.storyDescription.setText(sessionStory.description)
        currentStoryLayout.storyWeight.setText(sessionStory.weight.toString())
    }

    fun executeAction(session: Session, userCard: UserCard, isTableCard: Boolean) {
        if (isTableCard){
            when(userCard.action){
                "setWeight" -> updateStoryValue(session.session_id!!, userCard.value!!)
                "goToBreak" -> goToBreak(session)
                "addNote" -> addStoryNote(session.session_id!!, selectNote(userCard.name.toString()))
            }
        } else {
            when(userCard.action){
                "launchStory" -> {
                    if (unlockLaunchStory) {
                        showStoryListRecycler.postValue(true)
                    }
                }
                "repeatTurn" -> repeatTurn(session.session_id!!, currentStorySnapshot.value?.doc_id!!)
                "flipCards" -> flipCards(session.session_id!!, cardsOnTable)
                "finishSession" -> finishSession(session)
                "goToHome" -> goToHomeData.postValue(true)
                else -> setTableCard(userCard, session.session_id!!)
            }
        }
    }

    private fun selectNote(card_name: String): String {
        return if(card_name == "infinito") "Particionar historia" else "Redefinir historia con cliente"
    }

    fun setTableCard(card: UserCard, session_id: String){
        if (canSend){
            canSend = false
            apiController.setTableCard(card,session_id)
        }
    }

    fun createBacklog(session: Session) {
        apiController.createBacklogAPI(
            Backlog(
                ProjectUtils().generateTimeStamp(),
                "-",
                session.project_id,
                session.project_name,
                getBacklogStatus(),
                session.session_id
            )
        )
    }

    fun validateBacklog(session: Session){
        if (session.status == "complementary"){
            //Actualizar Backlog
        } else {
            createBacklog(session)
        }
    }

    fun addStoriesToBacklog(storyList: List<SessionStory>) {
        apiController.addStoriesToBacklog(storyList, backlogSnapshotData.value?.doc_id!!)
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
}