package com.example.chatapp.logIn

import com.example.chatapp.model.AppUser

 sealed interface LoginEvent {
    data object Idle : LoginEvent
    data object NavigateToRegister : LoginEvent
    data class NavigateToHome(val user: AppUser) : LoginEvent
    data object LoginSuccess : LoginEvent

    data object LoginFailed : LoginEvent




}