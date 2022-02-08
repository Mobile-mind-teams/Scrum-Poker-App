package com.example.scrumpokerapp.service

interface SnapshotService {
    fun getFirebaseSessionSnapshot()

    fun getFirebaseUserSessionListSnapshot(userEmail: String)

    fun getFirebaseCurrentStorySnapshot(session_id: String)
}