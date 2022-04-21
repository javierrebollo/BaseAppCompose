package com.javierrebollo.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javierrebollo.myapplication.data.db.dao.RoomDao
import com.javierrebollo.myapplication.data.db.entity.RoomDBO

@Database(entities = [RoomDBO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}
