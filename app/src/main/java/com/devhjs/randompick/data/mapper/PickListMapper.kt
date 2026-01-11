package com.devhjs.randompick.data.mapper

import com.devhjs.randompick.data.local.entity.PickListEntity
import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.model.PickList

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
