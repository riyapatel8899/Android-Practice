package com.example.animation8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StateTransitionUI()
        }
    }
}

enum class UIState { StateA, StateB, StateC }

@Composable
fun StateTransitionUI() {
    var currentState by remember { mutableStateOf(UIState.StateA) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getBackgroundColorForState(currentState)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(currentState = currentState)

        Button(
            onClick = { currentState = getNextState(currentState) },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Next State")
        }
    }
}

@Composable
fun AnimatedContent(currentState: UIState) {
    AnimatedVisibility(
        visible = currentState == UIState.StateA,
        enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + expandVertically(),
        exit = fadeOut(animationSpec = tween(durationMillis = 2000)) + shrinkVertically()
    ) {
        Text("This is ${currentState.name}", fontSize = 32.sp)
    }

    AnimatedVisibility(
        visible = currentState == UIState.StateB,
        enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + expandVertically(),
        exit = fadeOut(animationSpec = tween(durationMillis = 2000)) + shrinkVertically()
    ) {
        Text("This is ${currentState.name}", fontSize = 32.sp)
    }

    AnimatedVisibility(
        visible = currentState == UIState.StateC,
        enter = fadeIn(animationSpec = tween(durationMillis = 2000)) + expandVertically(),
        exit = fadeOut(animationSpec = tween(durationMillis = 2000)) + shrinkVertically()
    ) {
        Text("This is ${currentState.name}", fontSize = 32.sp)
    }
}

fun getBackgroundColorForState(state: UIState): Color {
    return when (state) {
        UIState.StateA -> Color.Red
        UIState.StateB -> Color.Green
        UIState.StateC -> Color.Blue
    }
}

fun getNextState(currentState: UIState): UIState {
    return when (currentState) {
        UIState.StateA -> UIState.StateB
        UIState.StateB -> UIState.StateC
        UIState.StateC -> UIState.StateA
    }
}