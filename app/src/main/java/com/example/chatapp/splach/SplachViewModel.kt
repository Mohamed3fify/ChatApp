package com.example.chatapp.splach

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.FirebaseUtils
import com.example.chatapp.model.AppUser
import com.example.chatapp.model.DataUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplachViewModel : ViewModel() {
    val event = mutableStateOf<SplachEvent>(SplachEvent.Idle)
    private val auth = Firebase.auth


    fun navigate() {
        if (auth.currentUser != null) {
            getUserFromFireStore(auth.currentUser?.uid ?: run {
                navigateToLogin()
                return
            })
        } else {
            navigateToLogin()
        }
    }

    private fun getUserFromFireStore(uid: String) {
        FirebaseUtils.getUser(uid, onSuccessListener = { docSnapshot ->
            val user = docSnapshot.toObject(AppUser::class.java)
            DataUtils.appUser = user
            navigateToHome(user!!)
        }, onFailureListener = {
            Log.e("TAG", "getUserFromFirestore: ${it.message}")
            navigateToLogin()
        })
    }

    private fun navigateToLogin() {
        event.value = SplachEvent.NavigateToLogin
    }

    private fun navigateToHome(user: AppUser) {
        event.value = SplachEvent.NavigateToChatBot(user)
    }
}