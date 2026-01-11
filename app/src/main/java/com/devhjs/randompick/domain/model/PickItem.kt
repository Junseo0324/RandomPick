package com.devhjs.randompick.domain.model

data class PickItem(
    val id: Int? = null,
    val listId: Int,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)