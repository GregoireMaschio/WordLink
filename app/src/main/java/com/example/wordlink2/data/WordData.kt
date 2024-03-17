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
//    fun chooseStartAndEndWords(dictionary: MutableList<Word>, maxAttempts: Int = 3): Pair<Word, Word>? {
//        repeat(maxAttempts) {
//            val startWord = findStartWord(dictionary) ?: return null
//            val endWord = buildLongestPath(startWord, dictionary)?.lastOrNull()
//            if (endWord != null) {
//                return Pair(startWord, endWord)
//            }
//        }
//        return null
//    }

//    private fun findStartWord(dictionary: MutableList<Word>): Word? {
//        val shortWords = mutableListOf<Word>()
//        for (word in dictionary) {
//            if (word.value.length in 2..4) {
//                shortWords.add(word)
//            }
//        }
//        return shortWords.randomOrNull()
//    }
//
//    private fun buildLongestPath(startWord: Word, dictionary: MutableList<Word>): List<Word>? {
//        val visited = mutableSetOf<String>()
//        visited.add(startWord.value)
//
//        val queue = ArrayDeque<Pair<Word, List<Word>>>()
//        queue.add(Pair(startWord, listOf(startWord)))
//
//        var longestPath: List<Word>? = null
//
//        while (queue.isNotEmpty()) {
//            val pair = queue.removeFirst()
//            val currentWord = pair?.first
//            val currentPath = pair?.second
//
//            // Check if pair is not null
//            if (currentWord == null || currentPath == null) {
//                continue
//            }
//
//            // Check if longestPath is already assigned
//            if (longestPath != null && currentPath.size < longestPath.size) {
//                continue  // Skip processing shorter paths
//            }
//
//            // Generate possible next words
//            for (nextLetter in 'a'..'z') {
//                for (i in 0..currentWord.value.length) {
//                    val nextWord = createWord(currentWord.value.substring(0, i) + nextLetter + currentWord.value.substring(i))
//                    if (nextWord.value in visited || !dictionary.contains(nextWord) || !isValidTransition(currentWord, nextWord)) {
//                        continue
//                    }
//                    val newPath = currentPath + nextWord
//                    longestPath = newPath
//                    queue.add(Pair(nextWord, newPath))
//                    visited.add(nextWord.value)
//                }
//            }
//        }
//        return longestPath
//    }
//
//    private fun isValidTransition(currentWord: Word, nextWord: Word): Boolean {
//        for ((char, count) in nextWord.characters) {
//            if (currentWord.characters.getOrDefault(char, 0) < count) {
//                return false
//            }
//        }
//        return true
//    }

}


fun main(){
    val url = "https://raw.githubusercontent.com/GregoireMaschio/WordLink/master/app/src/main/assets/liste_anglais.txt"
    val dict = fetchDictionary(url)
//    println(dict)
//    println(dict[1].value)

//    println(chooseStartAndEndWords(dict,10))
}

