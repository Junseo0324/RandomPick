package com.devhjs.randompick.core.data.mapper

import com.devhjs.randompick.core.data.local.entity.PickItemEntity
import com.devhjs.randompick.core.model.PickItem

fun PickItemEntity.toModel() = PickItem(
    id = id,
    listId = listId,
    name = name,
    createdAt = createdAt
)

fun PickItem.toEntity() = PickItemEntity(
    id = id ?: 0,
    listId = listId,
    name = name,
    createdAt = createdAt
)