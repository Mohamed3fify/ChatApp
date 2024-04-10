package com.example.chatapp.register

import com.example.chatapp.model.AppUser

sealed interface RegisterEvent {
    data object Idle : RegisterEvent
    data class NavigateToHome(val user: AppUser) : RegisterEvent
    data object NavigateToLogin : RegisterEvent

}