package com.example.wordlink2.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wordlink2.data.Word
import com.example.wordlink2.repository.DictionaryRepository
import com.example.wordlink2.ui.GameUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.ArrayList

class WordLinkViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUIState())

    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    private val dictionaryRepository:DictionaryRepository? = DictionaryRepository.get()

    val words: java.util.ArrayList<Word>
        get() = dictionaryRepository?.getWords() ?: ArrayList()

    val currentWordText: String
        get() = dictionaryRepository?.getWord(uiState.value.currentIndex)?.value ?: ""
}