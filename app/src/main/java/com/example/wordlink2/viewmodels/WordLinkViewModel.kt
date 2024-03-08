package com.example.wordlink2.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wordlink2.data.Word
import com.example.wordlink2.repository.DictionaryRepository
import com.example.wordlink2.ui.GameUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WordLinkViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUIState())

    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    private val dictionaryRepository:DictionaryRepository? = DictionaryRepository.get()

    private val _dictionary = mutableStateOf<List<Word>>(emptyList())
    val dictionary: State<List<Word>> = _dictionary

//    init {
//        fetchDictionary()
//    }

//    private fun fetchDictionary() {
//        repository?.let {
//            _dictionary.value = it.getWords()
//        }
//    }
}