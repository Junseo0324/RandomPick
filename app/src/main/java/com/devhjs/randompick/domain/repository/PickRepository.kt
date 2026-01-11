package com.devhjs.randompick.domain.repository

import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.model.PickList

interface PickRepository {
    suspend fun getLists(): List<PickList>
    suspend fun insertList(list: PickList): Long
    suspend fun updateList(list: PickList)
    suspend fun deleteList(list: PickList)

    suspend fun insertItem(item: PickItem): Boolean
    suspend fun updateItem(item: PickItem)
    suspend fun deleteItem(item: PickItem)
}