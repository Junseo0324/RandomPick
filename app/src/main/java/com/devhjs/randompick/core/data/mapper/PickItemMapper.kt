package com.devhjs.randompick.core.data.mapper

import com.devhjs.randompick.core.data.local.entity.PickItemEntity
import com.devhjs.randompick.core.model.PickItem

fun PickItemEntity.toModel(): PickItem =
    PickItem(id, listId, name, createdAt)

fun PickItem.toEntity(): PickItemEntity =
    PickItemEntity(id, listId, name, createdAt)