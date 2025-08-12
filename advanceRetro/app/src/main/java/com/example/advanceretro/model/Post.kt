package com.example.advanceretro.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int,
    val id: Int? = null,
    val title: String,
    val body: String
)