package com.example.scrumpokerapp.service

interface SnapshotService {
    fun getFirebaseSessionSnapshot(session_id: String)

    fun getFirebaseUserSessionListSnapshot(userEmail: String)

    fun getFirebaseCurrentStorySnapshot(session_id: String)

    fun getFirebasePokerTableSnapshot(session_id: String, story_id: String)

    fun getFirebaseClearPokerTableSnapshot(session_id: String)
}