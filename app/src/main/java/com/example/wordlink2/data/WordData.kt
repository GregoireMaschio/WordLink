package com.example.wordlink2.data

import android.content.Context
import android.util.Log
import com.example.wordlink2.data.GitHubService.fetchDictionary
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object GitHubService{

    fun fetchDictionary(urlString: String): MutableList<Word> {
            val wordList = mutableListOf<Word>()
            wordList.add(createWord("fuckkkkk"))
            try {
                val url = URL(urlString)
                val connection = url.openConnection()
                val inputStream = connection.getInputStream()
                val reader = BufferedReader(InputStreamReader(inputStream))

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    val word = createWord(line.orEmpty())
                    wordList.add(word)
                }

                reader.close()
                inputStream.close()
            } catch (e: Exception) {
                // Handle exceptions (e.g., network errors, IO errors)
                e.printStackTrace()
            }
            return wordList
    }

    fun fetchDictionaryFromFile(context: Context, fileName: String): MutableList<Word> {
        val wordList = mutableListOf<Word>()

        try {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val word = createWord(line.orEmpty())
                wordList.add(word)
            }

            reader.close()
            inputStream.close()

            Log.d("Fetch", "found it")
        } catch (e: Exception) {
            // Handle exceptions (e.g., file not found, IO errors)
            e.printStackTrace()
            Log.d("Fetch", "didnt found it")
        }

        val test = 0
        return wordList
    }

    private fun createWord(word: String): Word {
        val characterCounts = word.groupingBy { it }.eachCount()
        return Word(characterCounts, word)
    }
}


fun main(){
    val url = "https://raw.githubusercontent.com/GregoireMaschio/WordLink/master/app/src/main/java/com/example/wordlink2/assets/liste_anglais.txt"
    val dict = fetchDictionary(url)
    println(dict)
    println(dict[1].value)


}

