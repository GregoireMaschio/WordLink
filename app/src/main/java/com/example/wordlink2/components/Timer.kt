package com.example.wordlink2.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wordlink2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Timer(coroutineScope: CoroutineScope, duration: Int, isFrench: Boolean, onTimerFinished: () -> Unit) {
    var timerState by remember { mutableStateOf(duration) }

    DisposableEffect(Unit) {
        val job = coroutineScope.launch {
            repeat(duration) {
                delay(1000)
                timerState--
            }
            // Call the callback function when the timer finishes
            onTimerFinished()
        }

        onDispose {
            job.cancel()
        }
    }

    Text(
        text = stringResource(id = if (isFrench) R.string.timer_text_fr else R.string.timer_text_en)
    )

    Text(
        text = timerState.toString(),
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold
    )
}

