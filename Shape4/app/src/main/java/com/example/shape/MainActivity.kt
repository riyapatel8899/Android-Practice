package com.example.shape

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.enableDefaults()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        WaveShapeBox()
                    }
                }
            }
        }
    }

    class WaveShape : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val path = Path().apply {
                moveTo(0f, size.height)

                // Create wave using cubic curves (left to right)
                val waveHeight = size.height / 3
                val waveLength = size.width / 2

                cubicTo(
                    waveLength / 2, size.height - waveHeight,
                    waveLength / 2, waveHeight,
                    waveLength, size.height / 2
                )
                cubicTo(
                    waveLength + waveLength / 2, size.height + waveHeight,
                    waveLength + waveLength / 2, size.height - waveHeight,
                    size.width, size.height
                )

                // Close the bottom
                lineTo(size.width, size.height)
                lineTo(size.width, 0f)
                lineTo(0f, 0f)
                close()
            }

            return Outline.Generic(path)
        }
    }

    @Composable
    fun WaveShapeBox() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    color = Color.Blue,
                    shape = WaveShape()
                )
        )
    }
}
