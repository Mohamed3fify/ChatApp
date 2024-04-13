package com.example.chatapp.addRoom

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.FirebaseUtils
import com.example.chatapp.model.Category
import com.example.chatapp.model.Room

class AddRoomViewModel : ViewModel() {

    val roomNameState = mutableStateOf("")
    val roomNameErrorState = mutableStateOf<String?>(null)
    val roomDescriptionState = mutableStateOf("")
    val roomDescriptionErrorState = mutableStateOf<String?>(null)
    val isCategoryExpanded = mutableStateOf(false)
    val categoriesList = Category.getCategoryList()
    val selectedCategoryItem = mutableStateOf(categoriesList[0])
    val isLoading = mutableStateOf(false)
    val events = mutableStateOf<AddRoomEvent>(AddRoomEvent.Idle)

    fun addRoom() {
        if (validateFields()) {
            // Add Room to firestore
            isLoading.value = true
            val room = Room(
                name = roomNameState.value,
                description = roomDescriptionState.value, categoryId = selectedCategoryItem.value.id
            )
            FirebaseUtils.addRoom(room, onSuccessListener = {
                isLoading.value = false
                navigateBack()
            }, onFailureListener = {
                isLoading.value = false
                Log.e("TAG", "addRoom: ${it.message}")
            })
        }
    }

    fun navigateBack() {
        events.value = AddRoomEvent.NavigateBack
    }

    fun validateFields(): Boolean {
        if (roomNameState.value.isEmpty() || roomNameState.value.isBlank()) {
            roomNameErrorState.value = "Required"
            return false
        } else
            roomNameErrorState.value = null
        if (roomDescriptionState.value.isEmpty() || roomDescriptionState.value.isBlank()) {
            roomDescriptionErrorState.value = "Required"
            return false
        } else
            roomDescriptionErrorState.value = null
        return true
    }
}