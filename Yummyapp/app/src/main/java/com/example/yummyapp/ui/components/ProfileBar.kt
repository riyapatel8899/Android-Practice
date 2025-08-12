package com.example.yummyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yummyapp.R

@Composable
fun ProfileBar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = "Hello, Riya",
            style = MaterialTheme.typography.bodyLarge
        )
        Box(
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .size(50.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_notifications),
                contentDescription = null
            )
        }

    }
}