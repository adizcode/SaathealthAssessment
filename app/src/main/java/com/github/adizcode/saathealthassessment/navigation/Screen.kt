package com.github.adizcode.saathealthassessment.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("Auth")
    object Onboarding : Screen("Onboarding")
    object Dashboard : Screen("Dashboard")
    object Awards : Screen("Awards")
    object Video : Screen("Video")
}
