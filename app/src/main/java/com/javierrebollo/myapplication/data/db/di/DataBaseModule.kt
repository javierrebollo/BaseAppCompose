package com.javierrebollo.myapplication.data.db.di

import android.content.Context
import androidx.room.Room
import com.javierrebollo.myapplication.data.db.AppDatabase
import com.javierrebollo.myapplication.data.db.dao.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "app-database"
    ).build()

    @Provides
    fun provideRoomDao(
        appDatabase: AppDatabase
    ): RoomDao {
        return appDatabase.roomDao()
    }
}
