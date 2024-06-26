package com.example.chatapp.register


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.chatapp.logIn.LoginActivity
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.ChatAuthButton
import com.example.chatapp.utils.ChatAuthTextField
import com.example.chatapp.utils.ChatToolbar
import com.example.chatapp.utils.CustomDialog
import com.example.chatapp.utils.LoadingDialog

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                RegisterContent(onRegistrationSuccess = {
                    finishAffinity()
                }){
                    finish()
                }
            }
        }
    }
}

@Composable
fun RegisterContent(
    viewModel: RegisterViewModel = viewModel(),
    onRegistrationSuccess: () -> Unit,
    onFinish: () -> Unit
) {
    // new
    val accountAlreadyExists by viewModel.accountAlreadyExists.collectAsState()

    Scaffold(topBar = {
        ChatToolbar(title = "Sign Up") {
            onFinish()
        }
    }) { paddingValues ->
        paddingValues

        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(painterResource(id = R.drawable.chat_bg), contentScale = ContentScale.FillBounds)
             ,horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Spacer(Modifier.fillMaxHeight(0.35F))

                ChatAuthTextField(
                    state = viewModel.firstNameState,
                    error = viewModel.firstNameErrorState.value,
                    label = "first name",

                    )
                Spacer(modifier = Modifier.height(8.dp))
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

                //new
                Spacer(modifier = Modifier.height(8.dp))
                ChatAuthTextField(
                    state = viewModel.confirmPasswordState,
                    error = viewModel.confirmPasswordErrorState.value,
                    label = "confirm password",
                    isPassword = true
                )


                Spacer(
                    modifier = Modifier.height(12.dp),
                )
                ChatAuthButton(
                    title = "create account",

                    ) {
                    viewModel.register()
                }


                Spacer(modifier = Modifier.height(12.dp))

                // new
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Text(
                        text = "Already have an account ?",
                        color = Color.Black,
                        fontSize = 17.sp
                        //modifier = Modifier.padding(start = 8.dp)
                    )

                    // Spacer(modifier = Modifier.width(16.dp)) // Add space between text and button

                    TextButton(
                        onClick = {
                            viewModel.navigateToLogin()
                        },
                        // modifier = Modifier.padding(1.dp)
                    ) {
                        Text(
                            text = "login",
                            color = Color.Red,
                            fontSize = 17.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }



        }
        TriggerEvent(event = viewModel.events.value) {
            onRegistrationSuccess()
        }
        LoadingDialog(isLoading = viewModel.isLoading)

        if (accountAlreadyExists) {
            CustomDialog(
                title = "Account Already Exists",
                message = "An account with this email already exists.",
                onDismiss = {
                    viewModel.resetEvent()
                    viewModel.resetAccountAlreadyExists()
                }
            )
        }
    }


}

@Composable
fun TriggerEvent(
    event: RegisterEvent,
    viewModel: RegisterViewModel = viewModel(),
    onRegisterSuccess: () -> Unit
) {
    val context = LocalContext.current

    when (event) {
        RegisterEvent.Idle -> {}
        is RegisterEvent.NavigateToHome -> {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            onRegisterSuccess()
        }

        // new
        RegisterEvent.NavigateToLogin -> {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            viewModel.resetEvent()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterContent(onRegistrationSuccess = {}) {}
}
