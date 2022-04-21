package com.javierrebollo.myapplication.ui.screen.dummy.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.ui.theme.Size

@Composable
fun ListOfRooms(
    roomList: List<Room>,
    bookPressed: (room: Room) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = Size.horizontalItemPadding,
                vertical = Size.verticalItemPadding
            ),
            verticalArrangement = Arrangement.spacedBy(Size.verticalSpaceBetweenItems),
            modifier = Modifier
                .weight(1f)
        ) {

            items(roomList) { room ->
                RoomItem(
                    room = room,
                    bookPressed = bookPressed
                )
            }
        }
    }
}
