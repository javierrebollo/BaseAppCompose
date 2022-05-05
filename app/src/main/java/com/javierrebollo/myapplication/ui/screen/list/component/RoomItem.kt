package com.javierrebollo.myapplication.ui.screen.list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.javierrebollo.myapplication.R
import com.javierrebollo.myapplication.domain.entity.Room
import com.javierrebollo.myapplication.ui.theme.Primary
import com.javierrebollo.myapplication.ui.theme.Size

@Composable
fun RoomItem(
    room: Room,
    bookPressed: (room: Room) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                bookPressed.invoke(room)
            }
    ) {
        Box(
            modifier = Modifier
                .height(Size.roomImageHeight)
                .clip(RoundedCornerShape(Size.roomImageCornerRadius))
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    room.thumbnail
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = room.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.room_spots_remain, room.spots),
                    color = Primary
                )
            }

            Button(
                onClick = {
                    bookPressed.invoke(room)
                }
            ) {
                Text(text = stringResource(R.string.room_spots_book))
            }
        }
    }
}
