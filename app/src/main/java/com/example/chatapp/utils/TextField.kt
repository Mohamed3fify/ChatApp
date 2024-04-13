package com.example.chatapp.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.chatapp.R


@Composable
fun ChatAuthTextField(
    state: MutableState<String>,
    error: String?,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,

    ) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth(0.9F)) {


        OutlinedTextField(
            value = state.value,
            onValueChange = { state.value = it },
            modifier = Modifier.fillMaxWidth(.9F),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 17.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                errorIndicatorColor = Color.Red,

                ),

            label = {
                Text(
                    text = label, fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier.clickable {}
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),

            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,

            trailingIcon = {
                if (isPassword) {
                    PasswordVisibilityToggle(
                        isPasswordVisible = passwordVisible,
                        onToggleClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                    )
                }
            }
        )
        if (error != null) {
            Text(
                text = error, color = Color.Red,
                fontSize = 18.sp,
                modifier = Modifier.align(androidx.compose.ui.Alignment.Start)
            )
        }

    }
}

@Composable
fun PasswordVisibilityToggle(
    isPasswordVisible: Boolean,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onToggleClick,
        modifier = modifier
    ) {
        Icon(
            painter =
            if (isPasswordVisible) painterResource(id = R.drawable.key)
            else painterResource(id = R.drawable.key_off),
            contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password",
            tint = Color.Black
        )
    }
}





