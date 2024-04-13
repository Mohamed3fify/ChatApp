package com.example.chatapp.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.FirebaseUtils
import com.example.chatapp.model.Room

class HomeViewModel : ViewModel(){
    val event = mutableStateOf<HomeEvent>(HomeEvent.Idle)
    val isLoading = mutableStateOf(false)
    val roomsList = mutableStateListOf<Room>()


    fun getRoomsFromFirestore() {
        isLoading.value = true
        FirebaseUtils.getRooms(onSuccessListener = {
            isLoading.value = false
            roomsList.clear()
            val list = it.toObjects(Room::class.java)
            roomsList.addAll(list)
        }, onFailureListener = {
            isLoading.value = false
            Log.e("TAG", "getRoomsFromFirestore: ${it.message}")
        }
        )
    }

    fun navigateToChatScreen(room: Room) {
        event.value = HomeEvent.NavigateToChatScreen(room)
    }

    fun navigateToAddRoomScreen() {
        event.value = HomeEvent.NavigateToAddRoomScreen
    }

    fun resetEventState() {
        event.value = HomeEvent.Idle
    }
}