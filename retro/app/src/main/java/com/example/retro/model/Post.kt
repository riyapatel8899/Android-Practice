package com.example.retro.model

data class Post(
    val userId: Int,
    val id: Int = 0,
    val title: String,
    val body: String
)
