package com.devhjs.randompick.core.data.mapper

import com.devhjs.randompick.core.data.local.entity.PickListEntity
import com.devhjs.randompick.core.model.PickItem
import com.devhjs.randompick.core.model.PickList

fun PickListEntity.toModel(items: List<PickItem> = emptyList()): PickList =
    PickList(id, title, createdAt, items)

fun PickList.toEntity(): PickListEntity =
    PickListEntity(id, title, createdAt)
