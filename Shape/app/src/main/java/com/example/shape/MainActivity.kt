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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.asComposePath
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath


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
//                    CustomCircleShapeWithImage()
//                    CustomRoundedCornerShapeWithImage()
//                    BasicShapeCanvas()
                    RoundedShapeSmoothnessExample()
                }
            }
        }
    }
}
//
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
//
//@Composable
//fun CustomCircleShapeWithImage() {
//    val CircleShape = GenericShape { size, _ ->
//        addOval(androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height))
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ){
//        Box(
//            modifier = Modifier
//                .size(200.dp)
//                .clip(CircleShape)
//                .background(Color.Red)
//        )
//    }
//}

//@Composable
//fun CustomRoundedCornerShapeWithImage() {
//    val roundedShape = RoundedCornerShape(30.dp)
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = androidx.compose.ui.Alignment.Center
//    ) {
//        Box(
//            modifier = Modifier
//                .size(width = 250.dp, height = 150.dp)
//                .clip(roundedShape)
//                .background(Color.Red)
//        )
//    }
//}
//
//@Composable
//fun BasicShapeCanvas() {
//    Box(
//        modifier = Modifier
//            .drawWithCache {
//                val roundedPolygon = RoundedPolygon(
//                    numVertices = 6,
//                    radius = size.minDimension / 2,
//                    centerX = size.width / 2,
//                    centerY = size.height / 2
//                )
//                val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
//                onDrawBehind {
//                    drawPath(roundedPolygonPath, color = Color.Blue)
//                }
//            }
//            .fillMaxSize()
//    )
//}
//
@Composable
private fun RoundedShapeSmoothnessExample() {
    // [START android_compose_graphics_polygon_rounding_smooth]
    Box(
        modifier = Modifier
            .drawWithCache {
                val roundedPolygon = RoundedPolygon(
                    numVertices = 3,
                    radius = size.minDimension / 2,
                    centerX = size.width / 2,
                    centerY = size.height / 2,
                    rounding = CornerRounding(
                        size.minDimension / 10f,
                        smoothing = 0.1f
                    )
                )
                val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                onDrawBehind {
                    drawPath(roundedPolygonPath, color = Color.Black)
                }
            }
            .size(100.dp)
    )
}

