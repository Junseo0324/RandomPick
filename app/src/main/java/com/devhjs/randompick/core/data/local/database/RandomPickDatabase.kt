package com.devhjs.randompick.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhjs.randompick.core.data.local.dao.PickDao
import com.devhjs.randompick.core.data.local.entity.PickItemEntity
import com.devhjs.randompick.core.data.local.entity.PickListEntity

@Database(
    entities = [PickListEntity::class, PickItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RandomPickDatabase : RoomDatabase() {
    abstract fun pickDao(): PickDao
}