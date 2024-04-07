package com.example.wordlink2.repository

import android.content.Context
import com.example.wordlink2.data.GitHubService
import com.example.wordlink2.data.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DictionaryRepository private constructor(context: Context){

    var words: MutableList<Word> = mutableListOf()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            fetchDictionary("https://raw.githubusercontent.com/GregoireMaschio/WordLink/master/app/src/main/assets/liste_anglais.txt")
        }
    }

    private suspend fun fetchDictionary(url: String) {
        // Start a coroutine to fetch dictionary data
        val fetchedWords = withContext(Dispatchers.IO) {
            GitHubService.fetchDictionary(url)
        }
        words = fetchedWords.toMutableList()
        println("Words $words")
    }

    fun fetchNewDictionary(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val fetchedWords = GitHubService.fetchDictionary(url)
            words = fetchedWords.toMutableList()
            println("New Words $words")
        }
    }


//    var words: MutableList<Word> = fetchDictionaryFromFile(context,"liste_anglais.txt")
    fun getWords(): ArrayList<Word> {
        return ArrayList(words)
    }

    fun getWord(index: Int): Word {
        return words[index]
    }

    fun path():List<String>{
        return GitHubService.findPath(words)
    }

    fun getWordByValue(value: String): Word? {
        return words.find { it.value == value }
    }

    companion object{
        private var INSTANCE: DictionaryRepository? = null

        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE = DictionaryRepository(context)
            }
        }

        fun get(): DictionaryRepository? {
            return INSTANCE
        }
    }
}