package com.example.scrumpokerapp.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.scrumpokerapp.model.Backlog
import com.example.scrumpokerapp.model.Session
import com.example.scrumpokerapp.model.SessionStory
import com.example.scrumpokerapp.model.UserCard
import com.example.scrumpokerapp.service.DataBaseInstance
import com.example.scrumpokerapp.service.SnapshotService
import com.example.scrumpokerapp.service.response.BacklogResponse
import com.example.scrumpokerapp.service.response.TableCardResponse

const val DATABASE_INSTANCE_TYPE = "Firestore"

class SnapshotRepository: SnapshotService{

    var databaseInstance: DataBaseInstance
    val sessionSnapshotMutableLiveData : MutableLiveData<Session?>
    val currentStorySnapshotData : MutableLiveData<SessionStory?>
    val pokerTableSnapshotData : MutableLiveData<TableCardResponse?>
    val clearPokerTableSnapshotData : MutableLiveData<TableCardResponse?>
    val currentCardSentSnapshotData : MutableLiveData<UserCard?>
    val backlogCreatedSnapshotData : MutableLiveData<Backlog?>
    val updatedStorySnapshotData : MutableLiveData<SessionStory?>

    constructor(){
        databaseInstance = DataBaseInstance().initDataBaseInstance(DATABASE_INSTANCE_TYPE)
        sessionSnapshotMutableLiveData = MutableLiveData()
        currentStorySnapshotData = MutableLiveData()
        pokerTableSnapshotData = MutableLiveData()
        clearPokerTableSnapshotData = MutableLiveData()
        currentCardSentSnapshotData = MutableLiveData()
        backlogCreatedSnapshotData = MutableLiveData()
        updatedStorySnapshotData = MutableLiveData()
    }

    override fun getFirebaseSessionSnapshot(session_id: String){
        databaseInstance.firestoreInstance.collection("session")
            .whereEqualTo("session_id",session_id)
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
                    sessionSnapshotMutableLiveData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data user session: ${doc.data}")
                        sessionSnapshotMutableLiveData.postValue(
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
                    sessionSnapshotMutableLiveData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document user session")
                }
            }
    }

    override fun getFirebaseAdminSessionListSnapshot(adminUid: String) {
        databaseInstance.firestoreInstance.collection("session")
            .whereEqualTo("admin_id",adminUid)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    sessionSnapshotMutableLiveData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data admin session: ${doc.data}")
                        sessionSnapshotMutableLiveData.postValue(
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
                    sessionSnapshotMutableLiveData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document admin session")
                }
            }
    }

    override fun getFirebaseCurrentStorySnapshot(session_id: String) {
        databaseInstance.firestoreInstance.collection("session")
            .document(session_id)
            .collection("story")
            .whereEqualTo("visibility", true)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    currentStorySnapshotData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data story-session: ${doc.data}")
                        currentStorySnapshotData.postValue(
                            SessionStory(
                                doc.data?.get("title").toString(),
                                doc.data?.get("description").toString(),
                                doc.data?.get("weight").toString().toDouble(),
                                doc.data?.get("read_status") as Boolean,
                                doc.data?.get("agreed_status") as Boolean,
                                doc.data?.get("visibility") as Boolean,
                                doc.data?.get("note").toString(),
                                doc.id,
                            )
                        )
                    }

                } else {
                    currentStorySnapshotData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document story visibility true")
                }
            }
    }

    override fun getFirebaseUpdatedStorySnapshot(session_id: String) {
        databaseInstance.firestoreInstance.collection("session")
            .document(session_id)
            .collection("story")
            .whereEqualTo("agreed_status", true)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    updatedStorySnapshotData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data story-session: ${doc.data}")
                        updatedStorySnapshotData.postValue(
                            SessionStory(
                                doc.data?.get("title").toString(),
                                doc.data?.get("description").toString(),
                                doc.data?.get("weight").toString().toDouble(),
                                doc.data?.get("read_status") as Boolean,
                                doc.data?.get("agreed_status") as Boolean,
                                doc.data?.get("visibility") as Boolean,
                                doc.data?.get("note").toString(),
                                doc.id,
                            )
                        )
                    }

                } else {
                    updatedStorySnapshotData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document story agreed true")
                }
            }
    }

    override fun getFirebasePokerTableSnapshot(session_id: String, story_id: String) {
        databaseInstance.firestoreInstance.collection("session")
            .document(session_id)
            .collection("table-card")
            .whereEqualTo("story_id",story_id)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    pokerTableSnapshotData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    var cardsOnTable : ArrayList<UserCard> = arrayListOf()
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data table-card: ${doc.data}")
                        var card = UserCard(
                            doc.data?.get("user_id").toString(),
                            doc.data?.get("value").toString().toDouble(),
                            doc.data?.get("action").toString(),
                            doc.data?.get("visibility") as Boolean,
                            doc.data?.get("story_id").toString(),
                            doc.data?.get("name").toString(),
                            doc.id
                        )
                        cardsOnTable.add(card)
                    }

                    pokerTableSnapshotData.postValue(
                        TableCardResponse(
                            "session",
                            "Success!",
                            cardsOnTable
                        )
                    )

                } else {
                    pokerTableSnapshotData.postValue(null)
                    clearPokerTableSnapshotData.postValue(
                        TableCardResponse(
                            "session",
                            "Deleted!"
                        )
                    )
                    Log.d(ContentValues.TAG, "Cards Deleted")
                    Log.d(ContentValues.TAG, "No such document table-card")
                }
            }
    }

    override fun getFirebaseClearPokerTableSnapshot(session_id: String) {
        var isSuccess = true
        databaseInstance.firestoreInstance.collection("session")
            .document(session_id)
            .collection("table-card")
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    clearPokerTableSnapshotData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    Log.d(ContentValues.TAG, "Not Empty Jet Deleted")
                    clearPokerTableSnapshotData.postValue(null)
                    isSuccess = false
                }
            }.let {
                Log.d(ContentValues.TAG, "it: " + it.toString())
                if (isSuccess){
                    clearPokerTableSnapshotData.postValue(
                        TableCardResponse(
                            "session",
                            "Deleted!"
                        )
                    )
                    Log.d(ContentValues.TAG, "Deleted")
                }
            }
    }

    override fun getFirebaseCurrentCardSentSnapshot(session_id: String, user_id: String) {
        databaseInstance.firestoreInstance.collection("session")
            .document(session_id)
            .collection("table-card")
            .whereEqualTo("user_id", user_id)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    currentCardSentSnapshotData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data table-card: ${doc.data}")
                        currentCardSentSnapshotData.postValue(
                            UserCard(
                                doc.data?.get("user_id").toString(),
                                doc.data?.get("value").toString().toDouble(),
                                doc.data?.get("action").toString(),
                                doc.data?.get("visibility") as Boolean,
                                doc.data?.get("story_id").toString(),
                                doc.data?.get("name").toString(),
                                doc.id,
                            )
                        )
                    }

                } else {
                    currentCardSentSnapshotData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document table-card")
                }
            }
    }

    override fun getFirebaseCreatedBacklogSnapshot(session_id: String) {
        databaseInstance.firestoreInstance.collection("backlog")
            .whereEqualTo("sesion_id",session_id)
            .limit(1)
            .addSnapshotListener{ snapshot, e ->

                if (e != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", e)
                    backlogCreatedSnapshotData.postValue(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot.documents){
                        Log.d(ContentValues.TAG, "DocumentSnapshot data backlog: ${doc.data}")
                        backlogCreatedSnapshotData.postValue(
                            Backlog(
                                doc.data?.get("created_at").toString(),
                                doc.data?.get("modified_at").toString(),
                                doc.data?.get("project_id").toString(),
                                doc.data?.get("project_name").toString(),
                                doc.data?.get("status").toString(),
                                doc.data?.get("session_id").toString(),
                                doc.id
                            )
                        )
                    }

                } else {
                    backlogCreatedSnapshotData.postValue(null)
                    Log.d(ContentValues.TAG, "No such document backlog")
                }
            }
    }
}