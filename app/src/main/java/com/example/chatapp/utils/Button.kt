package com.example.chatapp.utils

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ChatAuthButton(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle = TextStyle(fontSize = 18.sp),
    isEnabled: Boolean = true, onClick: () -> Unit,
) {
    Button(
        modifier = if (isEnabled) modifier.fillMaxWidth(0.9F) else modifier
            .fillMaxWidth(0.9F)
            .shadow(
                shape = RoundedCornerShape(
                    6.dp
                ),
                elevation = 5.dp,
                ambientColor = Color.Blue,
            ),
        onClick = {
            onClick()
        },
        contentPadding = PaddingValues(horizontal = 36.dp, vertical = 18.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) Color.Blue else Color.White,
            contentColor = if (isEnabled) Color.Black else Color.White
        )
    ) {
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.weight(1F))

    }
}

@Composable
fun CreateButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.Black)
    ) {
        Text(
            text = "Create",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatAuthButtonPreview() {
    ChatAuthButton(title = "Login") {

    }
}
