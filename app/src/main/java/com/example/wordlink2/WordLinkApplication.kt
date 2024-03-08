package com.example.wordlink2

import android.app.Application
import com.example.wordlink2.repository.DictionaryRepository

class WordLinkApplication() :Application() {
    override fun onCreate(){
        super.onCreate()

        DictionaryRepository.initialize(this)
    }
}