package com.devhjs.randompick.core.data.mapper

import com.devhjs.randompick.core.data.local.entity.PickListEntity
import com.devhjs.randompick.core.model.PickItem
import com.devhjs.randompick.core.model.PickList

fun PickListEntity.toModel(items: List<PickItem>) = PickList(
    id = id,
    title = title,
    createdAt = createdAt,
    items = items
)

fun PickList.toEntity() = PickListEntity(
    id = id ?: 0,
    title = title,
    createdAt = createdAt
)
