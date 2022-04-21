package com.javierrebollo.myapplication.data.network.api

import com.javierrebollo.myapplication.data.network.response.BookListResponse
import com.javierrebollo.myapplication.data.network.response.BookRoomResponse
import retrofit2.Response
import retrofit2.http.GET

interface RoomApi {
    @GET("/rooms.json")
    suspend fun getRooms() : Response<BookListResponse>

    @GET("/bookRoom.json")
    suspend fun bookRoom() : Response<BookRoomResponse>
}
