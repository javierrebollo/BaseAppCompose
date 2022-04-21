package com.javierrebollo.myapplication.data.network.response

import com.javierrebollo.myapplication.domain.entity.Room

data class BookListResponse(
    val rooms: List<Room>
)
