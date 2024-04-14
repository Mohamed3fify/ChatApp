package com.example.chatapp.chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.FirebaseUtils
import com.example.chatapp.model.DataUtils
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.google.firebase.firestore.DocumentChange
import java.util.Date

class ChatViewModel : ViewModel() {
    val messagesListState = mutableStateListOf<Message>()
    val messageState = mutableStateOf("")
    var room: Room? = null
    fun sendAMessage() {
        if (messageState.value.isEmpty() || messageState.value.isBlank()) return

        val message = Message(
            content = messageState.value,
            senderName = DataUtils.appUser?.firstName,
            senderId = DataUtils.appUser?.uid,
            dateTime = Date().time,
            roomId = room?.id
        )
        FirebaseUtils.addMessage(message, onSuccessListener = {
            messageState.value = ""
        }, onFailureListener = {
            Log.e("TAG", "addAMessage: ")
        })
    }

    fun listenForMessages() {

        FirebaseUtils.getMessages(roomId = room?.id!!) { snapshots, error ->
            if (error != null) {
                Log.e("TAG", "listenForMessages: $error")
                return@getMessages
            }
            val list = mutableListOf<Message>()
            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        list.add(dc.document.toObject(Message::class.java))
                    }

                    DocumentChange.Type.MODIFIED -> {}
                    DocumentChange.Type.REMOVED -> {}
                }
            }
            list.addAll(messagesListState)
            messagesListState.clear()
            messagesListState.addAll(list)
        }
    }
}