package com.example.animation9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShapeMorphingAnimation()
        }
    }
}

@Composable
fun ShapeMorphingAnimation() {
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Canvas(modifier = Modifier.padding(40.dp).fillMaxSize()) {
        val sizeValue = size.width.coerceAtMost(size.height) / 2
        val squareRect = Rect(center = center, sizeValue)

        val morphedPath =
            interpolateShapes(progress = animationProgress.value, squareRect = squareRect)
        drawPath(morphedPath, color = Color.Blue, style = Fill)
    }
}

fun interpolateShapes(progress: Float, squareRect: Rect): Path {
    val path = Path()

    val cornerRadius = CornerRadius(
        x = lerp(start = squareRect.width / 2, stop = 0f, fraction = progress),
        y = lerp(start = squareRect.height / 2, stop = 0f, fraction = progress)
    )

    path.addRoundRect(
        roundRect = RoundRect(rect = squareRect, cornerRadius = cornerRadius)
    )

    return path
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}