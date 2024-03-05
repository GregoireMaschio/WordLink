package com.example.wordlink2.repository

import android.content.Context
import com.example.wordlink2.data.Word
import com.example.wordlink2.data.createDictionary

import  java.util.ArrayList
import java.util.Dictionary

class DictionaryRepository private constructor(context: Context){

    private val words: ArrayList<Word> = createDictionary()

    fun getWords():ArrayList<Word>{
        return words.clone() as ArrayList<Word>
    }

    fun getWord(index:Int): Word{
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