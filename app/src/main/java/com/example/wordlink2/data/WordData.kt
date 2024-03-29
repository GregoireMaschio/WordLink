package com.example.wordlink2.data

import android.content.Context
import android.util.Log
import com.example.wordlink2.data.GitHubService.fetchDictionary
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object GitHubService{

    private val backUpWords = listOf("en","ob","jo","ser","has","tea","os")

    fun fetchDictionary(urlString: String): MutableList<Word> {
            val wordList = mutableListOf<Word>()
            wordList.add(createWord("testestest"))
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

    fun findStartWord(dictionary: MutableList<Word>): Word? {
        val shortWords = mutableListOf<Word>()
        for (word in dictionary) {
            if (word.value.length in 2..3) {
                shortWords.add(word)
            }
        }
        return shortWords.randomOrNull()
    }

    fun findPath(dictionary: MutableList<Word>, maxDepth: Int = 8): List<String> {
        val startWord = findStartWord(dictionary)?.value ?: return emptyList()
        val path = mutableListOf(startWord)
        val resultPath = buildPath(dictionary, startWord, path, maxDepth)
        return if (resultPath.isEmpty() || resultPath.size < 3) {
            buildPathWithFallback(dictionary, maxDepth)
        } else {
            resultPath
        }
    }
    fun buildPath(dictionary: MutableList<Word>, currentWord: String, path: MutableList<String>, maxDepth: Int): List<String> {
        if (path.size >= maxDepth) {
            return path
        }

        val nextWords = mutableListOf<String>()

        for (word in dictionary) {
            if (word.value.length == currentWord.length + 1) {
                for (i in 0..currentWord.length) {
                    val newWord = currentWord.substring(0, i) + word.value[i] + currentWord.substring(i)
                    if (!path.contains(newWord) && dictionary.any { it.value == newWord }) {
                        nextWords.add(newWord)
                    }
                }
            }
        }

        for (nextWord in nextWords) {
            if (!path.contains(nextWord)) {
                path.add(nextWord)
                return buildPath(dictionary, nextWord, path, maxDepth)
            }
        }

        return path
    }

    fun buildPathWithFallback(dictionary: MutableList<Word>, maxDepth: Int = 8, maxRetries: Int = 3): List<String> {
        var retries = 0
        while (retries < maxRetries) {
            val startWord = backUpWords.random()
            val path = mutableListOf(startWord)
            val result = buildPath(dictionary, startWord, path, maxDepth)
            if (result.isEmpty() || result.size == 1) {
                return mutableListOf(backUpWords.random())
            }
            return result
            retries++
        }
        return emptyList()
    }

    fun buildPath(dictionary: MutableList<Word>, maxDepth: Int = 8): List<String> {
        val startWord = findStartWord(dictionary)?.value ?: return emptyList()
        val path = mutableListOf(startWord)
        return buildPath(dictionary, startWord, path, maxDepth)
    }

}


fun main(){
    val url = "https://raw.githubusercontent.com/GregoireMaschio/WordLink/master/app/src/main/assets/liste_anglais.txt"
    val dict = fetchDictionary(url)
//    println(dict)
//    println(dict[1].value)

//    println(GitHubService.findPath(dict))
    println(GitHubService.buildPath(dict,"has", mutableListOf("has"),8))
}

