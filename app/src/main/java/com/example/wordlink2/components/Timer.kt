package com.example.wordlink2.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Timer(coroutineScope: CoroutineScope, duration: Int) {
    var timerState by remember { mutableStateOf(duration) }

    DisposableEffect(Unit) {
        val job = coroutineScope.launch {
            repeat(duration) {
                delay(1000)
                timerState--
            }
        }

        onDispose {
            job.cancel()
        }
    }
    
    Text(text = "Remaining Time")

    Text(
        text = timerState.toString(),
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold
    )
}
