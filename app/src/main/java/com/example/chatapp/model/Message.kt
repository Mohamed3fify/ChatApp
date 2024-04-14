package com.example.chatapp.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Message(
    val content: String? = null,
    val senderId: String? = null,
    val senderName: String? = null,
    val dateTime: Long? = null,
    val roomId: String? = null,
){
    fun formatDateTime(): String {
        val date = Date(dateTime!!)
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(date)

    }

    companion object {
        const val COLLECTION_NAME = "Messages"
    }
}
