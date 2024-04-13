package com.example.chatapp.utils

import android.content.DialogInterface.OnClickListener
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.main_chat


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
                ambientColor = main_chat,
            ),
        onClick = {
            onClick()
        },
        contentPadding = PaddingValues(horizontal = 36.dp, vertical = 18.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) main_chat else Color.White,
            contentColor = if (isEnabled) Color.White else Color.White
        )
    ) {
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.weight(1F))

    }
}

@Composable
fun CreateButton(modifier: Modifier = Modifier,onClickListener: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(.8F),
        onClick = { onClickListener() },
        colors = ButtonDefaults.buttonColors(containerColor = main_chat, contentColor = Color.Black),
        shape = CircleShape
    ) {
        Text(
            text = stringResource(R.string.create),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )
    }
}

@Composable
fun SendButton(onClickListener: () -> Unit) {
    Button(
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = main_chat, contentColor = Color.White),
        onClick = { onClickListener() }) {
        Text(text = stringResource(R.string.icon_send_a_message))
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.send), contentDescription = stringResource(
                R.string.icon_send_a_message
            )
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatAuthButtonPreview() {
    ChatAuthButton(title = "Login") {

    }
}
