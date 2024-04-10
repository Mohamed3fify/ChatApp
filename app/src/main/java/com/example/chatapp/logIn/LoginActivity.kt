package com.example.chatapp.logIn

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.home.HomeActivity
import com.example.chatapp.register.RegisterActivity
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.ChatAuthButton
import com.example.chatapp.utils.ChatAuthTextField
import com.example.chatapp.utils.LoadingDialog
import com.example.chatapp.utils.Toolbar

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                loginContent {

                }
            }
        }
    }
}

@Composable
fun loginContent(viewModel: LoginViewModel = viewModel(), onFinish: () -> Unit) {

    Scaffold(topBar = {
        Toolbar(title = "log in")

    })
    { paddingValues ->
        paddingValues

        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .paint(painterResource(id = R.drawable.chat_bg), contentScale = ContentScale.FillBounds),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.fillMaxHeight(0.15F)) // Add padding below ChatToolbar


            Spacer(modifier = Modifier.fillMaxHeight(0.20F))
            Text(
                text = "Welcome",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 25.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))
            ChatAuthTextField(
                state = viewModel.emailState,
                error = viewModel.emailErrorState.value,
                label = "email"
            )
            Spacer(modifier = Modifier.height(8.dp))
            ChatAuthTextField(
                state = viewModel.passwordState,
                error = viewModel.passwordErrorState.value,
                    label = "password",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            ChatAuthButton(title = "login") {
                viewModel.login()
            }
            Spacer(modifier = Modifier.height(13.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Text(
                    text = "Don't have an account ?",
                    color = Color.Black,
                    fontSize = 17.sp
                    //modifier = Modifier.padding(start = 8.dp)
                )

                // Spacer(modifier = Modifier.width(16.dp)) // Add space between text and button

                TextButton(
                    onClick = {
                        viewModel.navigateToRegister()
                    },
                    // modifier = Modifier.padding(1.dp)
                ) {
                    Text(
                        text = "sign up",
                        color = Color.Red,
                        fontSize = 17.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }


        }
    }
    TriggerEvents(event = viewModel.events.value) {
        onFinish()
    }
    LoadingDialog(isLoading = viewModel.isLoading)


}

@Composable
fun TriggerEvents(
    event: LoginEvent,
    viewModel: LoginViewModel = viewModel(),
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    when (event) {
        LoginEvent.Idle -> {}

        // new
        is LoginEvent.LoginSuccess -> {
            AlertDialog(
                onDismissRequest = { viewModel.resetEvent() },
                title = { Text("Login Successful") },
                text = { Text("You have successfully logged in.") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.resetEvent()
                            viewModel.resetLoginSuccess()
                            onFinish()
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
        is LoginEvent.LoginFailed -> {
            AlertDialog(
                onDismissRequest = { viewModel.resetEvent() },
                title = { Text("Login Failed") },
                text = { Text("Email or Password is incorrect , try again or create a new account ") },

                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.navigateToRegister()
                        }
                    ) {
                        Text("Create Account")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            viewModel.resetEvent()
                            onFinish()
                        }
                    ) {
                        Text("Try again ")
                    }
                }
            )
        }

        is LoginEvent.NavigateToHome -> {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            onFinish()
        }

        LoginEvent.NavigateToRegister -> {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
            viewModel.resetEvent()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LoginPreview() {
    loginContent {

    }
}
