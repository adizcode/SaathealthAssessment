package com.github.adizcode.saathealthassessment.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adizcode.saathealthassessment.data.model.User
import com.github.adizcode.saathealthassessment.firebase.FirebaseService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private var _firebaseUser: FirebaseUser? = FirebaseService.auth.currentUser
    var user: User? = null
        private set

    init {
        FirebaseService.auth.addAuthStateListener {
            _firebaseUser = it.currentUser

            if (_firebaseUser != null) {
                viewModelScope.launch(exceptionHandler) {
                    user = FirebaseService.mapDocToUser(_firebaseUser!!.uid)
                }
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, err ->
        Log.d("Coroutine Error", err.toString())
    }

    fun register(email: String, password: String, onRegistrationDone: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            val user = FirebaseService.signUp(email, password)
            if (user != null) {
                onRegistrationDone()
                FirebaseService.updateUserDocument(
                    uid = user.uid,
                    userData = hashMapOf(
                        "firstName" to "",
                        "lastName" to "",
                        "mobileNum" to "",
                        "points" to 0,
                        "currentLevel" to 0,
                        "currentBadge" to 0,
                    )
                )
            }
        }
    }

    fun login(
        email: String,
        password: String,
        goToOnboarding: () -> Unit,
        goToDashboard: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val signedInUser = FirebaseService.signIn(email, password)

            if (signedInUser != null) {
                if (FirebaseService.mapDocToUser(signedInUser.uid)?.hasCredentialsSet() == true) {
                    goToDashboard()
                } else {
                    goToOnboarding()
                }
            }
        }
    }

    fun logout(goToAuth: () -> Unit) {
        FirebaseService.signOut()
        goToAuth()
    }

    fun updateFirstName(firstName: String) {
        // Update firstName field in the user document
        if (user != null) {
            user!!.firstName = firstName
            val map = user!!.toMap()
            viewModelScope.launch(exceptionHandler) {
                FirebaseService.updateUserDocument(user!!.uid, map)
            }
        }
    }

    fun updateLastName(lastName: String) {
        // Update lastName field in the user document
        if (user != null) {
            user!!.lastName = lastName
            val map = user!!.toMap()
            viewModelScope.launch(exceptionHandler) {
                FirebaseService.updateUserDocument(user!!.uid, map)
            }
        }
    }

    fun updateMobile(mobileNum: String) {
        // Update mobileNum field in the user document
        if (user != null) {
            user!!.mobileNum = mobileNum
            val map = user!!.toMap()
            viewModelScope.launch(exceptionHandler) {
                FirebaseService.updateUserDocument(user!!.uid, map)
            }
        }
    }

    fun incrementUserPoints() {
        if (user != null) {
            user!!.incrementPoints()
            val map = user!!.toMap()
            viewModelScope.launch(exceptionHandler) {
                FirebaseService.updateUserDocument(user!!.uid, map)
            }
        }
    }
}