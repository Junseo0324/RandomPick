package com.devhjs.randompick.core.model

data class PickList(
    val id: Int? = null,
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val items: List<PickItem> = emptyList()
)