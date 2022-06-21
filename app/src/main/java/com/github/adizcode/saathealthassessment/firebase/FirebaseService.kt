package com.github.adizcode.saathealthassessment.firebase

import com.github.adizcode.saathealthassessment.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object FirebaseService {
    private const val USERS_COLLECTION = "users"

    lateinit var auth: FirebaseAuth
        private set

    private lateinit var db: FirebaseFirestore

    fun initFirebaseAuth() {
        auth = Firebase.auth
        db = Firebase.firestore
    }

    suspend fun signUp(email: String, pass: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, pass).await().user
        }
    }

    suspend fun signIn(email: String, pass: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, pass).await().user
        }
    }

    // TODO: Create and use a sign out button
    fun signOut() = auth.signOut()

    suspend fun updateUserDocument(uid: String, userData: Map<String, Any>) {
        return withContext(Dispatchers.IO) {
            db.collection(USERS_COLLECTION).document(uid).set(userData)
        }
    }

    suspend fun mapDocToUser(uid: String): User? {
        return withContext(Dispatchers.IO) {
            val doc = db.collection(USERS_COLLECTION).document(uid).get().await()

            if (doc.data != null) {
                return@withContext User(
                    uid = uid,
                    userMap = doc.data!!
                )
            }
            null
        }
    }
}