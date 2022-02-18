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

class HomeViewModel (
    val apiController: ApiController,
    val application: Application
    ) : ViewModel() {

    val snapshotRepository: SnapshotRepository = SnapshotRepository()
    val sessionSnapshotData: MutableLiveData<Session?> = snapshotRepository.sessionSnapshotMutableLiveData
    val sesionListData: MutableLiveData<SessionResponse?> = apiController.sessionResponseMutableLiveData
    val sessionStatusData: MutableLiveData<Boolean> = MutableLiveData()

    val currentSession : MutableLiveData<Session> = MutableLiveData()

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
        when(session.status){
            "finished" -> {
                sessionStatusData.postValue(false)
                Toast.makeText(application,"La sesion ha terminado", Toast.LENGTH_SHORT).show()
            }
            "inProgress" -> {
                sessionStatusData.postValue(true)
                Toast.makeText(application,"Has entrado a la sesion", Toast.LENGTH_SHORT).show()
            }
            else -> {
                if(isProjectOwner){
                    updateSessionStatus(session)
                } else {
                    sessionStatusData.postValue(false)
                    Toast.makeText(application, obtenerMensaje(session.status), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun obtenerMensaje(status: String?): String {
        return  if(status == "new") "Aun no empieza la sesion!" else "Seguimos en Break!"
    }

    fun loadUserSessionSnapshot(email: String){
        snapshotRepository.getFirebaseUserSessionListSnapshot(email)
    }

    fun loadAdminSessionSnapshot(uid: String){
        snapshotRepository.getFirebaseAdminSessionListSnapshot(uid)
    }

}