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

    private val dictionaryRepository: DictionaryRepository? = DictionaryRepository.get()

    private val _dictionary = mutableStateOf<List<Word>>(emptyList())
    val dictionary: State<List<Word>> = _dictionary

    init {
        fetchDictionary()
    }
    fun changeDictionary(url: String) {
        dictionaryRepository?.fetchNewDictionary(url)
    }
    fun getWordByValue(value: String): Word? {
        return dictionaryRepository?.getWordByValue(value)
    }

    private fun fetchDictionary() {
        _dictionary.value = dictionaryRepository?.getWords()!!
    }

    fun isValidInput(previousWord: String, userInput: String, dictionary: MutableList<Word>): Boolean {
        if (userInput.length != previousWord.length) {
            return false
        }

        var diffCount = 0
        for (i in previousWord.indices) {
            if (previousWord[i] != userInput[i]) {
                diffCount++
                if (diffCount > 1) {
                    return false
                }
            }
        }
        if (diffCount != 1) {
            return false
        }
        return dictionary.any { it.value == userInput }
    }

    fun path(): List<String>? {
       return dictionaryRepository?.path()
    }
}