package com.example.animation7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnimatedGraphExample()
        }
    }
}

@Composable
fun AnimatedGraphExample() {
    var dataPoints by remember { mutableStateOf(generateRandomDataPoints(5)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        AnimatedLineGraph(dataPoints = dataPoints)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                dataPoints = generateRandomDataPoints(5)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text(
                "Update Data",
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun AnimatedLineGraph(dataPoints: List<Float>) {
    val animatableDataPoints = remember { dataPoints.map { Animatable(it) } }
    val path = remember { Path() }

    LaunchedEffect(dataPoints) {
        animatableDataPoints.forEachIndexed { index, animatable ->
            animatable.animateTo(dataPoints[index], animationSpec = TweenSpec(durationMillis = 500))
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        path.reset()
        animatableDataPoints.forEachIndexed { index, animatable ->
            val x = (size.width / (dataPoints.size - 1)) * index
            val y = size.height - (animatable.value * size.height)
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        drawPath(path, Color.Green, style = Stroke(5f))
    }
}

fun generateRandomDataPoints(size: Int): List<Float> {
    return List(size) { Random.nextFloat() }
}