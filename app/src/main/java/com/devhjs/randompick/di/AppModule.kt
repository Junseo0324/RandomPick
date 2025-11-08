package com.devhjs.randompick.di

import android.content.Context
import androidx.room.Room
import com.devhjs.randompick.core.data.local.dao.PickDao
import com.devhjs.randompick.core.data.local.database.RandomPickDatabase
import com.devhjs.randompick.core.data.repository.PickRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RandomPickDatabase =
        Room.databaseBuilder(
            appContext,
            RandomPickDatabase::class.java,
            "random_pick_db"
        ).build()

    @Provides
    @Singleton
    fun providePickDao(db: RandomPickDatabase) = db.pickDao()

    @Provides
    @Singleton
    fun providePickRepository(dao: PickDao) = PickRepository(dao)
}