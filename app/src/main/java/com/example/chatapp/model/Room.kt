package com.example.chatapp.model

data class Room(
    val name: String? = null,
    val description: String? = null,
    val categoryId: String? = null,
    var id: String? = null
) {
    companion object {
        val COLLECTION_NAME = "Rooms"
    }

}
