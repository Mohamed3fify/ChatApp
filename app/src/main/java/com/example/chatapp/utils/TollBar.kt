package com.example.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatToolbar(
    title: String,
    onNavigationIconClick: (() -> Unit)? = null ,

) {
    TopAppBar(title = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(70.dp)
                .padding(end = 50.dp)

        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
    }, navigationIcon = {
        if (onNavigationIconClick != null)
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(40.dp)
                    .clickable {
                        onNavigationIconClick()
                    }
                    .padding(8.dp)

            )
    },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Black

        )

    )


}

@Preview
@Composable
fun ChatToolbarPreview() {
    ChatToolbar(title = "login")
}