package com.example.animation2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DominoEffect()
        }
    }
}

@Composable
fun DominoEffect() {
    val animatedValues = List(6) { remember { Animatable(0f) } }

    LaunchedEffect(Unit) {
        animatedValues.forEachIndexed { index, animate ->
            animate.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, delayMillis = index * 100)
            )
        }
    }

    Box (modifier = Modifier.fillMaxSize()){
        animatedValues.forEachIndexed { index, value ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = ((index+1) * 50).dp, y = ((index+1) * 30).dp)
                    .background(getRandomColor(index).copy(alpha = value.value))
            )
        }
    }
}

fun getRandomColor(seed: Int): Color {
    val random = Random(seed = seed).nextInt(256)
    return Color(random, random, random)
}