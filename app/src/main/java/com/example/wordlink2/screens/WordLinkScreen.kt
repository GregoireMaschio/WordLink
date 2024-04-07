package com.example.wordlink2.screens

import HomeScreen
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordlink2.R
import com.example.wordlink2.viewmodels.WordLinkViewModel

enum class AppScreen(){
    HOME,
    GAME
}

@Preview
@Composable
fun WordLinkScreen(modifier:Modifier = Modifier){
    var currentScreen by remember { mutableStateOf("HOME") }
    var wordLinkViewModel = WordLinkViewModel()
    var path: List<String>? = emptyList()
    val context = LocalContext.current
//    println(path)
    val gameUIState by wordLinkViewModel.uiState.collectAsState()
    var isFrench by remember { mutableStateOf(false) }

    var isDialogOpen by remember { mutableStateOf(false) }
    var urlInput by remember { mutableStateOf("") }

    Surface(modifier){
        if (isDialogOpen) {
            ChangeDictionaryDialog(
                onDismiss = { isDialogOpen = false },
                onSubmit = {
                    wordLinkViewModel.changeDictionary(it)
                    isDialogOpen = false
                },
                isFrench = isFrench
            )
        }
        if(currentScreen == "HOME"){
            HomeScreen(
                wordLinkViewModel = wordLinkViewModel,
                onPlayClick = { currentScreen = "GAME"
                              path = wordLinkViewModel.path()},
                onChangeDictionaryClick = { isDialogOpen = true },
                onLanguageChangeClick = {
                                        isFrench =!isFrench
                },
                isFrench = isFrench)
        }else{
            GameScreen(
                onNavigateBack = {currentScreen = "HOME"},
                modifier=modifier,
                wordLinkViewModel = wordLinkViewModel,
                path = path,
                gameUIState = gameUIState,
                isFrench = isFrench
            )
        }
    }
}

@Composable
fun ChangeDictionaryDialog(
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit,
    isFrench: Boolean
) {
    var urlInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = if(isFrench) R.string.dictionnaire_btn_fr else R.string.dictionnaire_btn_en)) },
        text = {
            TextField(
                value = urlInput,
                onValueChange = { urlInput = it },
                label = { Text(stringResource(id = if(isFrench) R.string.url_fr else R.string.url_en)) }
            )
        },
        confirmButton = {
            Button(onClick = {
                onSubmit(urlInput)
            }) {
                Text(stringResource(id = if(isFrench) R.string.confirm_fr else R.string.confirm_en))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(stringResource(id = if(isFrench) R.string.cancel_fr else R.string.cancel_en))
            }
        }
    )
}