package com.devhjs.randompick.core.model

data class PickList(
    val id: Int,
    val title: String,
    val createdAt: Long,
    val items: List<PickItem> = emptyList()
)