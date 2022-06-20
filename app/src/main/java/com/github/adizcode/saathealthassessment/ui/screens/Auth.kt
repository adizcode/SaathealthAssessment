package com.github.adizcode.saathealthassessment.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.github.adizcode.saathealthassessment.navigation.Screen
import com.github.adizcode.saathealthassessment.ui.viewmodel.AppViewModel

@Composable
fun AuthScreen(navController: NavController, viewModel: AppViewModel) {
    val (email, setEmail) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }

    val showRegistrationForm = rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .clickable { focusManager.clearFocus() }
    ) {
        AuthFields(
            email = email,
            password = password,
            buttonText = if (showRegistrationForm.value) "Register" else "Login",
            toggleButtonText = if (showRegistrationForm.value) "I already have an account" else "I need to register",
            onButtonClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    if (showRegistrationForm.value) {
                        viewModel.register(email, password)
                        showRegistrationForm.value = false
                        Toast.makeText(
                            context,
                            "Successfully registered. Please login to use the app.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.login(email, password, navController)
                    }
                    focusManager.clearFocus()
                } else {
                    Toast.makeText(
                        context,
                        "Please enter email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            onToggleClick = { showRegistrationForm.value = !showRegistrationForm.value },
            onEmailChange = { setEmail(it) },
            onPasswordChange = { setPassword(it) }
        )
    }
}

@Composable
fun AuthFields(
    email: String,
    password: String,
    buttonText: String,
    toggleButtonText: String,
    onButtonClick: () -> Unit,
    onToggleClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column {
        OutlinedTextField(
            value = email,
            placeholder = { Text(text = "user@email.com") },
            label = { Text(text = "email") },
            onValueChange = onEmailChange,
        )

        OutlinedTextField(
            value = password,
            placeholder = { Text(text = "password") },
            label = { Text(text = "password") },
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = onButtonClick) {
            Text(buttonText)
        }

        Button(onClick = onToggleClick) {
            Text(toggleButtonText)
        }
    }
}