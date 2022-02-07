package com.example.scrumpokerapp.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.service.DataBaseInstance
import com.example.scrumpokerapp.service.SnapshotService

const val DATABASE_INSTANCE_TYPE = "Firestore"

class SnapshotRepository: SnapshotService{

    var databaseInstance: DataBaseInstance
    val sessionSnapshotMutableLiveData : MutableLiveData<Session?>
    val userSessionSnapdhotData : MutableLiveData<Session?>

    constructor(){
        databaseInstance = DataBaseInstance().initDataBaseInstance(DATABASE_INSTANCE_TYPE)
        sessionSnapshotMutableLiveData = MutableLiveData()
        userSessionSnapdhotData = MutableLiveData()
    }

    override fun getFirebaseSessionSnapshot(){
        databaseInstance.firestoreInstance.collection("session")
            .whereEqualTo("status","finished")
            .limit(1)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    sessionSnapshotMutableLiveData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data session: ${doc.data}")
                        sessionSnapshotMutableLiveData.postValue(
                            Session(
                                doc.data?.get("project_name").toString(),
                                doc.data?.get("project_id").toString(),
                                doc.id,
                                doc.data?.get("status").toString(),
                            )
                        )
                    }

                } else {
                    sessionSnapshotMutableLiveData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document session")
                }
            }
    }

    override fun getFirebaseUserSessionListSnapshot(userEmail: String) {
        databaseInstance.firestoreInstance.collection("session")
            .whereArrayContains("team",userEmail)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    userSessionSnapdhotData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data session: ${doc.data}")
                        userSessionSnapdhotData.postValue(
                            Session(
                                doc.data?.get("project_name").toString(),
                                doc.data?.get("project_id").toString(),
                                doc.id,
                                doc.data?.get("status").toString(),
                                doc.data?.get("team") as List<String>
                            )
                        )
                    }

                } else {
                    userSessionSnapdhotData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document session")
                }
            }
    }
}