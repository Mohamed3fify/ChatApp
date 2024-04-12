package com.example.chatapp.model

import com.example.chatapp.R

data class Category(
    val name: String? = null,
    val id: String? = null,
    val image: Int? = null
) {
    companion object {
        val MUSIC = "MUSIC"
        val MOVIES = "MOVIES"
        val SPORTS = "SPORTS"

        fun getCategoryList(): List<Category> {
            return listOf(
                fromId(MUSIC),
                fromId(MOVIES),
                fromId(SPORTS)

            )
        }

        fun fromId(id: String): Category {
            return when (id) {
                MUSIC -> Category("Music", MUSIC, R.drawable.music)
                SPORTS -> Category("Sports", SPORTS, R.drawable.sports)
                MOVIES -> Category("Movies", MOVIES, R.drawable.movies)
                else -> Category("Movies", MOVIES, R.drawable.movies)
            }
        }


    }
}
