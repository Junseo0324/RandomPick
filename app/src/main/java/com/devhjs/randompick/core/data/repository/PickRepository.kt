package com.devhjs.randompick.core.data.repository


import com.devhjs.randompick.core.data.local.dao.PickDao
import com.devhjs.randompick.core.data.local.entity.PickItemEntity
import com.devhjs.randompick.core.data.local.entity.PickListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PickRepository @Inject constructor(
    private val dao: PickDao
) {
    suspend fun getLists() = dao.getLists()
    suspend fun getItemsByListId(listId: Int) = dao.getItemsByListId(listId)
    suspend fun insertList(list: PickListEntity) = dao.insertList(list)
    suspend fun insertItem(item: PickItemEntity) = dao.insertItem(item)
    suspend fun deleteList(list: PickListEntity) = dao.deleteList(list)
    suspend fun deleteItem(item: PickItemEntity) = dao.deleteItem(item)
}