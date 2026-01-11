package com.devhjs.randompick.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.devhjs.randompick.data.local.entity.PickItemEntity
import com.devhjs.randompick.data.local.entity.PickListEntity

data class PickListWithItems(
    @Embedded val pickList: PickListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId"
    )
    val items: List<PickItemEntity>
)
