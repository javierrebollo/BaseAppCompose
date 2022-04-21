package com.javierrebollo.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javierrebollo.myapplication.data.db.entity.RoomDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT * FROM roomdbo")
    fun getAll(): Flow<List<RoomDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencyList: List<RoomDBO>)

    @Query("DELETE FROM roomdbo")
    fun nukeTable()
}
