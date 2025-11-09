package com.devhjs.randompick.core.model

data class PickItem(
    val id: Int? = null,
    val listId: Int,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)