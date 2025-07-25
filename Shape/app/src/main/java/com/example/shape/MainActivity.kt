package com.example.shape

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.enableDefaults()
        setContent {
            MaterialTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
//                    CustomTriangleShapeWithImage()
//                    CustomRectangleShapeWithImage()
//                    CustomSquareShapeWithImage()
                    CustomCircleShapeWithImage()
                }
            }
        }
    }
}

//@Composable
//fun CustomTriangleShapeWithImage() {
//    val triangleShape = GenericShape { size, _ ->
//        moveTo(size.width / 2f, 0f) // Top-center
//        lineTo(0f, size.height) // Bottom-left
//        lineTo(size.width, size.height) // Bottom-right
//        close()
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ){
//        Box(
//            modifier = Modifier
//                .size(200.dp)
//                .clip(triangleShape)
//                .background(Color.Red)
//        )
//    }
//}
//
//@Composable
//fun CustomRectangleShapeWithImage() {
//    val RectangleShape = GenericShape { size, _ ->
//        moveTo(0f, 0f)
//        lineTo(size.width, 0f)
//        lineTo(size.width, size.height)
//        lineTo(0f, size.height)
//        close()
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ){
//        Box(
//            modifier = Modifier
//                .size(width = 300.dp, height = 150.dp)
//                .clip(RectangleShape)
//                .background(Color.Red)
//        )
//    }
//}
//
//@Composable
//fun CustomSquareShapeWithImage() {
//    val SquareShape = GenericShape { size, _ ->
//        moveTo(0f, 0f)
//        lineTo(size.width, 0f)
//        lineTo(size.width, size.height)
//        lineTo(0f, size.height)
//        close()
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ){
//        Box(
//            modifier = Modifier
//                .size(200.dp)
//                .clip(SquareShape)
//                .background(Color.Red)
//        )
//    }
//}

@Composable
fun CustomCircleShapeWithImage() {
    val CircleShape = GenericShape { size, _ ->
        addOval(androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height))
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.Red)
        )
    }
}
