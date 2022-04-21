package com.javierrebollo.myapplication.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javierrebollo.myapplication.domain.entity.Room

@Entity
data class RoomDBO(
    @PrimaryKey val name: String,
    val spots: Int,
    val thumbnail: String
)

fun RoomDBO.toRoom(): Room {
    return Room(
        name = name,
        spots = spots,
        thumbnail = thumbnail
    )
}

fun Room.toRoomDBO(): RoomDBO {
    return RoomDBO(
        name = name,
        spots = spots,
        thumbnail = thumbnail
    )
}
