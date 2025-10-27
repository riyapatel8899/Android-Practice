package com.example.animation3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val items = (1..50).map { "Item $it" }
            InteractiveTimeline(timelineItems = items)
        }
    }
}

@Composable
fun InteractiveTimeline(timelineItems: List<String>) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        itemsIndexed(timelineItems) { index, item ->
            val animatableAlpha = remember { Animatable(0f) }
            val isVisible = remember {
                derivedStateOf {
                    scrollState.firstVisibleItemIndex <= index
                }
            }

            LaunchedEffect(isVisible.value) {
                if (isVisible.value) {
                    animatableAlpha.animateTo(
                        1f, animationSpec = tween(durationMillis = 1000)
                    )

                }
            }

            TimelineItem(
                text = item,
                alpha = animatableAlpha.value,
            )
        }
    }
}

@Composable
fun TimelineItem(text: String, alpha: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray.copy(alpha = alpha))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}