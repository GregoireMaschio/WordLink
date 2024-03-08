package com.example.wordlink2.repository

import android.content.Context
import com.example.wordlink2.data.GitHubService.fetchDictionary
import com.example.wordlink2.data.Word

class DictionaryRepository private constructor(context: Context){

    var words: MutableList<Word> = fetchDictionary("https://raw.githubusercontent.com/GregoireMaschio/WordLink/master/app/src/main/java/com/example/wordlink2/assets/liste_anglais.txt")

    fun getWords(): ArrayList<Word> {
        return ArrayList(words)
    }

    fun getWord(index: Int): Word {
        return words[index]
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
fun main() {
//    // Initialize the repository
//    DictionaryRepository.initialize()
//
//    // Get the repository instance
//    val repository = DictionaryRepository.get()
//
//    // Example usage: Get words from the repository
//    val words = repository?.getWords()
//    words?.forEach { println(it) }
//
//    // Example usage: Get a specific word from the repository
//    val word = repository?.getWord(0)
//    println("First word: $word")
}