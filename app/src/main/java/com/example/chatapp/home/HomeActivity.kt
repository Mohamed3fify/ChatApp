package com.example.chatapp.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.ChatToolbar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.chatapp.Constants
import com.example.chatapp.addRoom.AddRoomActivity
import com.example.chatapp.chat.ChatActivity
import com.example.chatapp.model.Category
import com.example.chatapp.model.Room
import androidx.lifecycle.viewmodel.compose.viewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeContent()
        }
    }
}

@Composable
fun HomeContent(viewModel: HomeViewModel = viewModel()) {
    Scaffold(
        topBar = {
            ChatToolbar(title = stringResource(R.string.chat_app))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.navigateToAddRoomScreen()
                },
                shape = CircleShape,
                containerColor = Color.Blue,
                modifier = Modifier.size(60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = stringResource(
                        R.string.icon_add_room
                    ),
                )
            }
        }
    ) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.chat_bg),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = paddingValues.calculateTopPadding())
                .padding(top = 32.dp)
        )
        {
            RoomsLazyGrid()
        }
    }
    TriggerEvent(event = viewModel.event.value)
}

@Composable
fun RoomsLazyGrid(viewModel: HomeViewModel = viewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getRoomsFromFirestore()
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.roomsList.size) { position ->
            // RoomCard
            RoomCard(room = viewModel.roomsList[position])
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomCard(room: Room, viewModel: HomeViewModel = viewModel()) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        onClick = {
            viewModel.navigateToChatScreen(room)
        }
    ) {
        Image(
            painter = painterResource(
                id = Category.fromId(room.categoryId ?: Category.MOVIES).image ?: R.drawable.movies
            ), contentDescription = stringResource(R.string.room_category_image),
            modifier = Modifier
                .size(86.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Text(
            text = room.name ?: "", fontSize = 14.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview
@Composable
private fun RoomPreview() {
    RoomCard(room = Room("Android Room", Category.SPORTS))
}

@Composable
fun TriggerEvent(
    event: HomeEvent,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    when (event) {
        HomeEvent.Idle -> {}

        HomeEvent.NavigateToAddRoomScreen -> {
            val intent = Intent(context, AddRoomActivity::class.java)
            context.startActivity(intent)
            viewModel.resetEventState()
        }

        is HomeEvent.NavigateToChatScreen -> {
            val intent = Intent(context, ChatActivity::class.java)
            // Parcelable
           // intent.putExtra(Constants.ROOM_KEY, event.room)
            context.startActivity(intent)
            viewModel.resetEventState()
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun GreetingPreview2() {
    ChatAppTheme {
        HomeContent()
    }
}