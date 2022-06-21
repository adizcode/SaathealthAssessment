package com.github.adizcode.saathealthassessment.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseService {
    private var auth: FirebaseAuth? = null
    var currentUser: FirebaseUser? = auth?.currentUser
        private set

    fun initFirebaseAuth() {
        auth = Firebase.auth
    }

    fun signUp(email: String, pass: String, onSuccess: () -> Unit, onError: () -> Unit) {
        val authResultTask = auth?.createUserWithEmailAndPassword(email, pass)

        if (authResultTask != null) {
            authResultTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }
        } else {
            onError()
        }
    }

    fun signIn(email: String, pass: String, onSuccess: () -> Unit, onError: () -> Unit) {
        val authResultTask = auth?.signInWithEmailAndPassword(email, pass)

        if (authResultTask != null) {
            authResultTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    currentUser = authResultTask.result.user
                    onSuccess()
                } else {
                    onError()
                }
            }
        } else {
            onError()
        }
    }

    fun signOut() = auth?.signOut()
}