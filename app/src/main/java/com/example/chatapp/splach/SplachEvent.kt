package com.example.chatapp.splach

import com.example.chatapp.model.AppUser

sealed interface SplachEvent {
    data object Idle : SplachEvent
    data object NavigateToLogin : SplachEvent
    data class NavigateToChatBot(val user: AppUser) : SplachEvent
}