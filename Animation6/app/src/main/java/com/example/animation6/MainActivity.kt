package com.example.animation6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.io.path.moveTo
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GestureControlledWaveform()
        }
    }
}

@Composable
fun GestureControlledWaveform() {
    var amplitude by remember { mutableStateOf(100f) }
    var frequency by remember { mutableStateOf(1f) }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { _, dragAmount ->
                amplitude += dragAmount.y
                frequency += dragAmount.x / 500f
                // Adjusting frequency based on drag
            }
        }
        .background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF003366), Color.White, Color(0xFF66B2FF))
            )
        )) {
        val width = size.width
        val height = size.height
        val path = Path()

        val halfHeight = height / 2
        val waveLength = width / frequency

        path.moveTo(0f, halfHeight)

        for (x in 0 until width.toInt()) {
            val theta = (2.0 * Math.PI * x / waveLength).toFloat()
            val y = halfHeight + amplitude * sin(theta.toDouble()).toFloat()
            path.lineTo(x.toFloat(), y)
        }

        val gradient = Brush.horizontalGradient(
            colors = listOf(Color.Blue, Color.Cyan, Color.Magenta)
        )

        drawPath(
            path = path,
            brush = gradient
        )
    }
}