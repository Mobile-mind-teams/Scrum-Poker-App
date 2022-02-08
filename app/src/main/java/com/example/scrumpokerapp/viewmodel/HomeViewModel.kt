package com.example.scrumpokerapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrumpokerapp.controller.ApiController
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.repository.SnapshotRepository
import com.example.scrumpokerapp.service.response.SessionResponse
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.activity.MainActivity
import com.example.scrumpokerapp.view.fragment.SessionFragment

class HomeViewModel (
    val apiController: ApiController,
    val application: Application
    ) : ViewModel() {

    val snapshotRepository: SnapshotRepository = SnapshotRepository()
    val userSessionData: MutableLiveData<Session?> = snapshotRepository.userSessionSnapdhotData
    val sesionListData: MutableLiveData<SessionResponse?> = apiController.sessionResponseMutableLiveData
    val sesionUpdateData: MutableLiveData<SessionResponse?> = apiController.sessionUpdateMutableLiveData
    val sessionStatusData: MutableLiveData<Boolean> = MutableLiveData()

    var currentSessionID : String = ""

    fun getAllUserSessions(uid: String){
        apiController.getAllUserSessions(uid)
    }

    fun getAllSessions(po_id: String){
        apiController.getAllSessions(po_id)
    }

    fun getSessionList(userProfile: UserProfile?){
        if (ProjectUtils().isProjectOwner(userProfile?.role)){
            getAllSessions(
                userProfile?.uid.toString()
            )
        } else {
            getAllUserSessions(
                userProfile?.email.toString()
            )
        }
    }

    fun updateSessionStatus(session: Session){
        session.status = "inProgress"
        apiController.updateSession(session)
    }

    fun processActionByUserRole(session: Session, isProjectOwner: Boolean) {
        if (isProjectOwner && !validateSessionStatus(session)
        ){
            updateSessionStatus(session)
        } else if (!isProjectOwner && !validateSessionStatus(session)){
            Toast.makeText(application,"Aun no empieza la Sesion!", Toast.LENGTH_SHORT).show()
            sessionStatusData.postValue(false)
        } else if (validateSessionStatus(session)){
            sessionStatusData.postValue(true)
        }
    }

    private fun validateSessionStatus(session: Session): Boolean {
        return session.status == "inProgress"
    }

    fun loadUserSessionSnapshot(email: String){
        snapshotRepository.getFirebaseUserSessionListSnapshot(email)
    }

}