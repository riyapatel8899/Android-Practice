package com.example.loadingimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoadingImgText()
        }
    }
}

@Composable
fun LoadingImgText(){
    var step by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        repeat(Int.MAX_VALUE){
            delay(2000)
            step = when (step) {
                0 -> 1
                1 -> 2
                else -> 0
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = step,
            transitionSpec = {
                fadeIn(animationSpec = tween(700, easing = LinearEasing)) togetherWith
                fadeOut(animationSpec = tween(700, easing = LinearEasing)) using
                SizeTransform(clip = false)
            },
            contentAlignment = Alignment.Center
        ) { s ->
            when (s) {
                0 -> Loading()
                1 -> Image()
                2 -> TextValue()
            }
        }
    }
}

@Composable
fun Loading(){

    val rotation = rememberInfiniteTransition().animateFloat(0f,360f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    ).value

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(modifier = Modifier.size(60.dp)) {
            val sw = 3.dp.toPx()
            drawArc(
                color = Color.Gray,
                startAngle = rotation,
                sweepAngle = 270f,
                useCenter = false,
                size = Size(size.width - sw, size.height- sw),
                style = Stroke(sw, cap = StrokeCap.Round)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text("Loading", color = Color.Gray, fontSize = 16.sp)
    }
}

@Composable
fun Image(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Loaded", color = Color.Gray, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.dog),
            contentDescription = null,
            modifier = Modifier
                .width(260.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TextValue(){
    Text(
        text = "Dog",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}
