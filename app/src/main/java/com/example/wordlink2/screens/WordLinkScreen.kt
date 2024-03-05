package com.example.wordlink2.screens

import HomeScreen
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
    val context = LocalContext.current

    val gameUIState by wordLinkViewModel.uiState.collectAsState()

    Surface(modifier){
        if(currentScreen == "HOME"){
            HomeScreen(
                onPlayClick = { currentScreen = "GAME" },
                onChangeDictionaryClick = { /*TODO*/ }) {

            }
        }else{
            GameScreen(
                onNavigateBack = {currentScreen = "HOME"},
                modifier=modifier,
                wordLinkViewModel = wordLinkViewModel,
                gameUIState = gameUIState,
            )
        }
    }
}