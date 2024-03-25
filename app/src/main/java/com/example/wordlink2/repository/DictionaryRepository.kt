package com.example.wordlink2.repository

import android.content.Context
import com.example.wordlink2.data.GitHubService
import com.example.wordlink2.data.GitHubService.fetchDictionaryFromFile
import com.example.wordlink2.data.Word

class DictionaryRepository private constructor(context: Context){

//    var words: MutableList<Word> = fetchDictionary("https://raw.githubusercontent.com/GregoireMaschio/WordLink/master/app/src/main/java/com/example/wordlink2/assets/liste_anglais.txt")
    var words: MutableList<Word> = fetchDictionaryFromFile(context,"liste_anglais.txt")
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