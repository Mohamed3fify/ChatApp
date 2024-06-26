package com.example.chatapp.logIn

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.FirebaseUtils
import com.example.chatapp.model.AppUser
import com.example.chatapp.model.DataUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(false)
    val events = mutableStateOf<LoginEvent>(LoginEvent.Idle)
    val auth = Firebase.auth
    val loginSuccess = mutableStateOf(false)



    fun login(){
        if (validateFields()) {
            isLoading.value = true
            auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        isLoading.value = false
                            events.value = LoginEvent.LoginFailed

                        // invalid email exception
                        /*else if (task.exception is FirebaseAuthInvalidUserException ) {

                            events.value = LoginEvent.LoginFailedAccountNotFound
                        }*/


                        Log.e("TAG", "error -> ${task.exception?.message}")
                        return@addOnCompleteListener
                    }
                    val uid = task.result.user?.uid
                    // get User from Firestore
                    getUserFromFirestore(uid!!)
                }
        }
    }
    private fun getUserFromFirestore(uid: String) {
        FirebaseUtils.getUser(uid, onSuccessListener = { documentSnapshot ->
            isLoading.value = false
            loginSuccess.value = true
            val user = documentSnapshot.toObject(AppUser::class.java)
            DataUtils.appUser = user
            navigateToHome(user!!)
        }, onFailureListener = {
            isLoading.value = false
        })
    }
     fun validateFields(): Boolean {

        if (emailState.value.isEmpty() && emailState.value.isBlank()) {
            emailErrorState.value = "Required"
            return false
        } else {
            emailErrorState.value = null
        }
        if (passwordState.value.isEmpty() && passwordState.value.isBlank()) {
            passwordErrorState.value = "Required"
            return false
        } else {
            passwordErrorState.value = null
        }
        if (passwordState.value.length < 6) {
            passwordErrorState.value = "Password can't be less that 6 Chars or numbers"
            return false
        } else {
            passwordErrorState.value = null
        }
        return true

    }
    fun navigateToRegister() {
        events.value = LoginEvent.NavigateToRegister

    }

    fun navigateToHome(user: AppUser) {
        events.value = LoginEvent.NavigateToHome(user)
    }

    fun resetEvent() {
        events.value = LoginEvent.Idle
    }
    fun resetLoginSuccess() {
        loginSuccess.value = false
    }

}