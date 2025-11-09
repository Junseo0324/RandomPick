package com.devhjs.randompick.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devhjs.randompick.core.data.local.entity.PickItemEntity
import com.devhjs.randompick.core.data.local.entity.PickListEntity

@Dao
interface PickDao {

    // List
    @Query("SELECT * FROM pick_list ORDER BY createdAt DESC")
    suspend fun getLists(): List<PickListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: PickListEntity): Long

    @Update
    suspend fun updateList(list: PickListEntity)

    @Delete
    suspend fun deleteList(list: PickListEntity)


    // Item
    @Query("SELECT * FROM pick_item WHERE listId = :listId ORDER BY createdAt DESC")
    suspend fun getItemsByListId(listId: Int): List<PickItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PickItemEntity)

    @Update
    suspend fun updateItem(item: PickItemEntity)

    @Delete
    suspend fun deleteItem(item: PickItemEntity)


    // 리스트 삭제 시 아이템 다 삭제
    @Query("DELETE FROM pick_item WHERE listId = :listId")
    suspend fun deleteItemsByListId(listId: Int)
}