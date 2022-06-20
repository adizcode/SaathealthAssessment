package com.github.adizcode.saathealthassessment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.github.adizcode.saathealthassessment.navigation.Screen

class AppViewModel : ViewModel() {

    fun register(email: String, password: String) {

    }

    fun login(email: String, password: String, navController: NavController) {
        login(email, password)

        var nextScreen: Screen = Screen.Onboarding

        // If user's first name, last name and mobile are all set
        // Navigate to dashboard
        if (false) {
            nextScreen = Screen.Dashboard
        }
        // Else navigate to onboarding

        navController.navigate(nextScreen.route)

    }

    private fun login(email: String, password: String) {

    }
}