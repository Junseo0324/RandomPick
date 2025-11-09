package com.devhjs.randompick.core.data.repository


import com.devhjs.randompick.core.data.local.dao.PickDao
import com.devhjs.randompick.core.data.mapper.toEntity
import com.devhjs.randompick.core.data.mapper.toModel
import com.devhjs.randompick.core.model.PickItem
import com.devhjs.randompick.core.model.PickList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PickRepository @Inject constructor(
    private val dao: PickDao
) {
    // List
    suspend fun getLists(): List<PickList> {
        return dao.getLists().map { listEntity ->
            val items = dao.getItemsByListId(listEntity.id).map { it.toModel() }
            listEntity.toModel(items)
        }
    }

    suspend fun insertList(list: PickList) = dao.insertList(list.toEntity())

    suspend fun updateList(list: PickList) = dao.updateList(list.toEntity())


    suspend fun deleteList(list: PickList) {
        dao.deleteItemsByListId(list.id ?: 0)
        dao.deleteList(list.toEntity())
    }

    // Item
    suspend fun getItemsByListId(listId: Int): List<PickItem> =
        dao.getItemsByListId(listId).map { it.toModel() }

    suspend fun insertItem(item: PickItem) =
        dao.insertItem(item.toEntity())

    suspend fun updateItem(item: PickItem) =
        dao.updateItem(item.toEntity())

    suspend fun deleteItem(item: PickItem) = dao.deleteItem(item.toEntity())


}