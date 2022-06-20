package com.github.adizcode.saathealthassessment.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.adizcode.saathealthassessment.navigation.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val (firstName, setFirstName) = rememberSaveable { mutableStateOf("") }
    val (lastName, setLastName) = rememberSaveable { mutableStateOf("") }
    val (mobileNum, setMobileNum) = rememberSaveable { mutableStateOf("") }

    val pageState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() }) {
        Spacer(Modifier.weight(0.4f))
        HorizontalPager(modifier = Modifier.weight(0.2f), count = 3, state = pageState) {
            val (value, setValue, label) = when (it) {
                0 -> Triple(firstName, setFirstName, "First Name")
                1 -> Triple(lastName, setLastName, "Last Name")
                else -> Triple(mobileNum, setMobileNum, "Mobile Number")
            }
            OutlinedTextField(
                value = value,
                onValueChange = setValue,
                label = { Text(label) },
            )
        }
        Spacer(Modifier.weight(0.3f))
        Row(
            Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .padding(horizontal = 60.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            if (pageState.currentPage > 0) {
                Button(onClick = {
                    scope.launch {
                        pageState.animateScrollToPage(pageState.currentPage - 1)
                    }
                }) {
                    Text("Back")
                }
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = {
                if (pageState.currentPage < 2) {
                    scope.launch {
                        pageState.animateScrollToPage(pageState.currentPage + 1)
                    }
                } else {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Auth.route) {
                            inclusive = true
                        }
                    }
                }
            }) {
                Text("Next")
            }
        }
    }
}