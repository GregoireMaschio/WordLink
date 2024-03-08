package com.example.wordlink2.data

import com.example.wordlink2.data.GitHubService.fetchDictionary
import okhttp3.OkHttpClient
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object GitHubService{
    private val client = OkHttpClient()

    fun fetchDictionary(urlString: String): MutableList<Word> {
        val wordList = mutableListOf<Word>()

        try {
            println("Fetching dictionary from URL: $urlString")
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
        println("Dictionary fetch completed.")
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

