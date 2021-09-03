package br.com.shido.recipecompose.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

enum class HeartButtonState {
    IDLE, ACTIVE
}


@Composable
fun HeartAnimated() {

    val currentState = remember {
        mutableStateOf(HeartButtonState.IDLE)
    }

    val transition1 = updateTransition(currentState, "label")

    val size by transition1.animateFloat(label = "animFloat") { state ->
        when (state.value) {
            HeartButtonState.ACTIVE -> 80F
            HeartButtonState.IDLE -> 50F
        }
    }



    val size2 by animateFloatAsState(
        targetValue = size, animationSpec =
        repeatable(
            1,
            animation = tween(100, 0, FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    val color by transition1.animateColor(label = "animColor") { state ->
        when (state.value) {
            HeartButtonState.ACTIVE -> Color.Red
            HeartButtonState.IDLE -> Color.Gray
        }
    }

    val rememberEnabled = remember {
        true
    }



    fun onClickCircle() {
        when(currentState.value){
            HeartButtonState.IDLE -> {
                currentState.value = HeartButtonState.ACTIVE
             }

            HeartButtonState.ACTIVE -> {
                currentState.value = HeartButtonState.IDLE
            }

        }

    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = rememberEnabled, onClick = {
                onClickCircle()
            })
            .height(55.dp)
    ) {
        drawCircle(
            radius = size2,
            brush = SolidColor(color)
        )
    }



}