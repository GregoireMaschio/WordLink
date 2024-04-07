package com.example.wordlink2.screens

import WordInput
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordlink2.R
import com.example.wordlink2.components.Timer
import com.example.wordlink2.data.Word
import com.example.wordlink2.ui.GameUIState
import com.example.wordlink2.viewmodels.WordLinkViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    wordLinkViewModel: WordLinkViewModel = WordLinkViewModel(),
    path: List<String>?,
    gameUIState: GameUIState = GameUIState(),
    isFrench: Boolean

){
    val dictionaryState: State<List<Word>> = wordLinkViewModel.dictionary
    val dictionary: List<Word> = dictionaryState.value
    println(path)

    var startWord by remember { mutableStateOf(path?.get(0) ?: "") }
    var enteredWords by remember { mutableStateOf(listOf<String>()) }
    var validEnteredWords by remember { mutableStateOf(if (startWord.isNotEmpty()) listOf(startWord) else emptyList()) }
    var endWord by remember { mutableStateOf(path?.lastOrNull() ?:"") }
    var word by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var gameWon by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(false) }
    var gameLost by remember { mutableStateOf(false) }

    val handleGameLoss: () -> Unit = {
        gameLost = true
    }

    if (gameLost) {
        AlertDialog(
            onDismissRequest = { gameLost = false },
            title = { Text(stringResource(id = if(isFrench) R.string.lost_fr else R.string.lost_en)) },
            confirmButton = {
                Button(
                    onClick = {
                        gameLost = false
                        onNavigateBack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "WordLink",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithText(label = stringResource(id = if(isFrench) R.string.start_word_fr else R.string.start_word_en), text = startWord)
            LabelWithText(label = stringResource(id = if(isFrench) R.string.target_word_fr else R.string.target_word_en), text = endWord)
        }

        Timer(coroutineScope,60,isFrench,handleGameLoss)

        // Entered Words
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            enteredWords.forEachIndexed { index, enteredWord ->
                val backgroundColor = when {
                    enteredWord == endWord -> Color.Green
                    enteredWord in path.orEmpty() -> Color(0xFFFFA500)
                    else -> Color.Red
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = enteredWord,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                if (index < enteredWords.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (enteredWords.lastOrNull() == endWord && enteredWords.size > 1) {
            gameWon = true
        }

        if (gameWon) {
            AlertDialog(
                onDismissRequest = {
                    gameWon = false
                   },
                title = { Text(stringResource(id = if(isFrench) R.string.win_fr else R.string.win_en)) },
                confirmButton = {
                    Button(
                        onClick = {
                            gameWon = false
                            onNavigateBack()}
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        // Input field
        WordInput(
            value = word,
            onValueChange = {
                word = it
                if (word.isNotEmpty() && (word.length != validEnteredWords.last().length + 1)) {
                    showWarning = true
                } else {
                    showWarning = false
                }
            },
            onSubmitted = {
                if (word.isNotEmpty() && (word.length == validEnteredWords.last().length + 1)) {
                    if (path != null) {
                        if(path.contains(word)){
                            validEnteredWords = validEnteredWords.toMutableList().apply { add(word) }
                        }
                    }
                    enteredWords = enteredWords.toMutableList().apply { add(word) }
                    word = ""
                    showWarning = false
                } else {
                    showWarning = true
                }
            },
            label = stringResource(id = if(isFrench) R.string.input_fr else R.string.input_en)
        )

        // Show warning if necessary
        if (showWarning) {
            Text(
                text = stringResource(id = if(isFrench) R.string.warning_text_fr else R.string.warning_text_en),
                color = Color.Red,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        // Add button with click listener
        Button(
            onClick = {
                if (word.isNotEmpty() && (word.length == validEnteredWords.last().length + 1)) {
                    if (path != null) {
                        if(path.contains(word)){
                            validEnteredWords = validEnteredWords.toMutableList().apply { add(word) }
                        }
                    }
                    enteredWords = enteredWords.toMutableList().apply { add(word) }
                    word = ""
                    showWarning = false
                } else {
                    showWarning = true
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(id = if(isFrench) R.string.add_btn_fr else R.string.add_btn_en))
        }



        //Home Button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { onNavigateBack() },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Back to Home")
            }
        }
    }
}

@Composable
fun LabelWithText(label: String, text: String) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(label)
        Text(text)
    }
}
