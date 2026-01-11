package com.devhjs.randompick.data.repository


import com.devhjs.randompick.data.local.dao.PickDao
import com.devhjs.randompick.data.mapper.toEntity
import com.devhjs.randompick.data.mapper.toModel
import com.devhjs.randompick.domain.model.PickItem
import com.devhjs.randompick.domain.model.PickList
import com.devhjs.randompick.domain.repository.PickRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

const val MAX_ITEMS = 10

@Singleton
class PickRepositoryImpl @Inject constructor(
    private val dao: PickDao
) : PickRepository {

    override fun getLists(): Flow<List<PickList>> {
        return dao.getLists().map { lists ->
            lists.map { it.pickList.toModel(it.items.map { item -> item.toModel() }) }
        }
    }

    override suspend fun insertList(list: PickList): Long = dao.insertList(list.toEntity())

    override suspend fun updateList(list: PickList) = dao.updateList(list.toEntity())


    override suspend fun deleteList(list: PickList) {
        dao.deleteItemsByListId(list.id ?: 0)
        dao.deleteList(list.toEntity())
    }

    override suspend fun insertItem(item: PickItem): Boolean {
        val count = dao.getItemCountByListId(item.listId)

        return if (count < MAX_ITEMS) {
            dao.insertItem(item.toEntity())
            true
        } else {
            false
        }

    }

    override suspend fun updateItem(item: PickItem) =
        dao.updateItem(item.toEntity())

    override suspend fun deleteItem(item: PickItem) = dao.deleteItem(item.toEntity())


}