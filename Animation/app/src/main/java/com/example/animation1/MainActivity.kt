package com.example.animation1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.foundation.layout.Box

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GameCharacterMovement()
        }
    }
}

@Composable
fun GameCharacterMovement() {
    val startPosition = Offset(100f, 100f)
    val endPosition = Offset(250f, 400f)
    val controlPoint = remember { mutableStateOf(Offset(200f, 300f)) }
    val position = remember { Animatable(startPosition, Offset.VectorConverter) }

    LaunchedEffect(controlPoint.value) {
        position.animateTo(
            targetValue = endPosition,
            animationSpec = keyframes {
                durationMillis = 5000
                controlPoint.value at 2500
            }
        )
    }

    val onControlPointChange: (offset: Offset) -> Unit = {
        controlPoint.value = it
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Icon(
            Icons.Filled.Face, contentDescription = "Localized description", modifier = Modifier
                .size(50.dp)
                .offset(x = position.value.x.dp, y = position.value.y.dp)
        )

        DraggableControlPoint(controlPoint.value, onControlPointChange)
    }
}

@Composable
fun DraggableControlPoint(controlPoint: Offset, onControlPointChange: (Offset) -> Unit) {
    var localPosition by remember { mutableStateOf(controlPoint) }
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = localPosition.x.roundToInt() - 15,
                    y = localPosition.y.roundToInt() - 15
                )
            }
            .size(30.dp)
            .background(Color.Red, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures(onDragEnd = {
                    onControlPointChange(localPosition)
                }) {_, dragAmount ->
                    val newX = (localPosition.x + dragAmount.x).coerceIn(0f, 600f)
                    val newY = (localPosition.y + dragAmount.y).coerceIn(0f, 600f)
                    localPosition = Offset(newX, newY)
                }
            }
    )
}

