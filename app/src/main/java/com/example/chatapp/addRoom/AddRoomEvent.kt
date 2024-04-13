package com.example.chatapp.addRoom

sealed interface AddRoomEvent {
    data object Idle : AddRoomEvent
    data object NavigateBack : AddRoomEvent
}