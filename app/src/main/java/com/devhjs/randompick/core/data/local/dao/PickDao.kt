package com.devhjs.randompick.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devhjs.randompick.core.data.local.entity.PickItemEntity
import com.devhjs.randompick.core.data.local.entity.PickListEntity

@Dao
interface PickDao {
    @Query("SELECT * FROM pick_list ORDER BY createdAt DESC")
    fun getLists(): List<PickListEntity>

    @Query("SELECT * FROM pick_item WHERE listId = :listId ORDER BY createdAt DESC")
    fun getItemsByListId(listId: Int): List<PickItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: PickListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PickItemEntity)

    @Delete
    suspend fun deleteList(list: PickListEntity)

    @Delete
    suspend fun deleteItem(item: PickItemEntity)
}