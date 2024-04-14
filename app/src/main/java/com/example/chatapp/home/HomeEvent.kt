package com.example.chatapp.home

import com.example.chatapp.model.Room

sealed interface HomeEvent {
    data object Idle : HomeEvent
    data object NavigateToAddRoomScreen : HomeEvent

    data class NavigateToChatScreen(val room: Room) : HomeEvent

}