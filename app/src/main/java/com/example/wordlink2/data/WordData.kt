package com.example.wordlink2.data

import android.content.Context
import android.util.Log
import com.example.wordlink2.data.GitHubService.fetchDictionary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object GitHubService{

    suspend fun fetchDictionary(urlString: String): MutableList<Word> = coroutineScope {
        val wordList = mutableListOf<Word>()

        try {
            val fetchedWords = withContext(Dispatchers.IO) {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpsURLConnection
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))

                val fetchedWordsDeferred = async(Dispatchers.IO) {
                    val words = mutableListOf<Word>()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        val word = createWord(line.orEmpty())
                        words.add(word)
                    }
                    words
                }
                val result = fetchedWordsDeferred.await()
                reader.close()
                inputStream.close()
                result
            }
            wordList.addAll(fetchedWords)
        } catch (e: Exception) {
            // Handle exceptions (e.g., network errors, IO errors)
            e.printStackTrace()
        }

        wordList
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

    private fun findStartWord(dictionary: MutableList<Word>): Word? {
        val shortWords = dictionary.filter { it.value.length in 2..3}
        return shortWords.randomOrNull()
    }

    fun findPath(dictionary: MutableList<Word>, maxDepth: Int = 8): List<String> {
        var startWord = findStartWord(dictionary)?.value ?: return emptyList()
        var path = mutableListOf(startWord)
        var resultPath = buildPath(dictionary, startWord, path, maxDepth)

        while (resultPath.isEmpty() || resultPath.size < 3) {
            startWord = findStartWord(dictionary)?.value ?: return emptyList()
            path = mutableListOf(startWord)
            resultPath = buildPath(dictionary, startWord, path, maxDepth)
        }

        return resultPath
    }
    fun buildPath(dictionary: MutableList<Word>, currentWord: String, path: MutableList<String>, maxDepth: Int): List<String> {
        if (path.size >= maxDepth) {
            return path.sortedBy { it.length }
        }

        val nextWords = mutableListOf<String>()

        for (word in dictionary) {
            if (word.value.length == currentWord.length + 1 && isExtensionOf(word, currentWord)) {
                val newWord = word.value
                if (!path.contains(newWord)) {
                    nextWords.add(newWord)
                }
            }
        }

        for (nextWord in nextWords) {
            if (!path.contains(nextWord)) {
                path.add(nextWord)
                buildPath(dictionary, nextWord, path, maxDepth)
            }
        }

        return filterWordsFromPath(path.sortedBy { it.length })
    }

    private fun isExtensionOf(word: Word, currentWord: String): Boolean {
        val currentChars = currentWord.toCharArray()
        val wordChars = word.value.toCharArray()

        for (i in currentChars.indices) {
            if (wordChars[i] != currentChars[i]) {
                return wordChars.copyOfRange(i + 1, wordChars.size).contentEquals(currentChars.copyOfRange(i, currentChars.size))
            }
        }
        return true
    }

    private fun filterWordsFromPath(path: List<String>): List<String> {
        if (path.size < 2) return emptyList()

        val firstWord = path.first()
        val lastWord = path.last()

        fun containsOnlyLetters(word: String, firstWord: String, lastWord: String): Boolean {
            val lettersToCheck = (firstWord + lastWord).toSet()
            return word.all { it in lettersToCheck }
        }

        return path.filter { containsOnlyLetters(it, firstWord, lastWord) }
    }

}


suspend fun main(){
    val url = "https://raw.githubusercontent.com/GregoireMaschio/WordLink/master/app/src/main/assets/liste_francais.txt"
    val dict = fetchDictionary(url)
//    println(dict)
//    println(dict[1].value

    println(GitHubService.findPath(dict))
//    val word = "pan"
//    println(GitHubService.buildPath(dict,word, mutableListOf(word),8))

}

