package com.met.nearby.store.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.met.nearby.store.R
import com.met.nearby.store.auth.UserSession
import com.met.nearby.store.repository.AuthRepository
import com.met.nearby.store.repository.AuthResult

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val authRepository = remember { AuthRepository() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black2))
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nearby Store",
            color = colorResource(R.color.gold),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.black3),
                focusedContainerColor = colorResource(R.color.black3),
                unfocusedBorderColor = colorResource(R.color.gray),
                focusedBorderColor = colorResource(R.color.gold),
                unfocusedLabelColor = colorResource(R.color.gray),
                focusedLabelColor = colorResource(R.color.gold),
                cursorColor = colorResource(R.color.gold),
                focusedTextColor = colorResource(R.color.white),
                unfocusedTextColor = colorResource(R.color.white)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            if (passwordVisible) R.drawable.ic_visibility
                            else R.drawable.ic_visibility_off
                        ),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = colorResource(R.color.gray)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.black3),
                focusedContainerColor = colorResource(R.color.black3),
                unfocusedBorderColor = colorResource(R.color.gray),
                focusedBorderColor = colorResource(R.color.gold),
                unfocusedLabelColor = colorResource(R.color.gray),
                focusedLabelColor = colorResource(R.color.gold),
                cursorColor = colorResource(R.color.gold),
                focusedTextColor = colorResource(R.color.white),
                unfocusedTextColor = colorResource(R.color.white)
            )
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage!!,
                color = colorResource(R.color.gold),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Please enter email and password"
                    return@Button
                }

                isLoading = true
                errorMessage = null

                authRepository.authenticate(email, password).observeForever { result ->
                    when (result) {
                        is AuthResult.Loading -> {
                            isLoading = true
                        }
                        is AuthResult.Success -> {
                            isLoading = false
                            val user = result.user
                            UserSession.login(
                                id = user.Id,
                                email = user.Email,
                                firstName = user.FirstName,
                                lastName = user.LastName,
                                imageUrl = user.ImageUrl,
                                favorites = user.FavoriteStores
                            )
                            onLoginClick()
                        }
                        is AuthResult.Error -> {
                            isLoading = false
                            errorMessage = result.message
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.gold)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = colorResource(R.color.black2),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Login",
                    color = colorResource(R.color.black2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onSkipClick,
            enabled = !isLoading
        ) {
            Text(
                text = "Continue as Guest",
                color = colorResource(R.color.gray),
                fontSize = 14.sp
            )
        }
    }
}
