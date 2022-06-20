package com.github.adizcode.saathealthassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.adizcode.saathealthassessment.navigation.Screen
import com.github.adizcode.saathealthassessment.ui.screens.AwardsScreen
import com.github.adizcode.saathealthassessment.ui.screens.DashboardScreen
import com.github.adizcode.saathealthassessment.ui.screens.AuthScreen
import com.github.adizcode.saathealthassessment.ui.screens.OnboardingScreen
import com.github.adizcode.saathealthassessment.ui.screens.VideoScreen
import com.github.adizcode.saathealthassessment.ui.theme.SaathealthAssessmentTheme
import com.github.adizcode.saathealthassessment.ui.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AppRootComposable()
        }
    }

    @Composable
    fun AppRootComposable() {
        SaathealthAssessmentTheme {

            /* TODO: Abstract away navigation details */

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.Auth.route) {
                composable(Screen.Auth.route) { AuthScreen(navController, viewModel) }
                composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
                composable(Screen.Dashboard.route) { DashboardScreen(navController) }
                composable(Screen.Awards.route) { AwardsScreen(navController) }
                composable(Screen.Video.route) { VideoScreen(navController) }
            }
        }
    }
}