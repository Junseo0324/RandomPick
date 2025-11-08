package com.devhjs.randompick.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pick_item")
data class PickItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val listId: Int,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
)
