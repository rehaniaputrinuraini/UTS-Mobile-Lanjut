package com.example.unscramble.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class WordsRepository(private val context: Context) {

    private val database = AppDatabase.getDatabase(context)
    private val wordDao = database.wordDao()

    // Default words (bawaan aplikasi)
    private val defaultWords = setOf(
        "animal", "auto", "anecdote", "alphabet", "all", "awesome", "arise",
        "balloon", "basket", "bench", "best", "birthday", "book", "briefcase",
        "camera", "camping", "candle", "cat", "cauliflower", "chat", "children",
        "class", "classic", "classroom", "coffee", "colorful", "cookie",
        "creative", "cruise", "dance", "daytime", "dinosaur", "doorknob",
        "dine", "dream", "dusk", "eating", "elephant", "emerald", "eerie",
        "electric", "finish", "flowers", "follow", "fox", "frame", "free",
        "frequent", "funnel", "green", "guitar", "grocery", "glass", "great",
        "giggle", "haircut", "half", "homemade", "happen", "honey", "hurry",
        "hundred", "ice", "igloo", "invest", "invite", "icon", "introduce",
        "joke", "jovial", "journal", "jump", "join", "kangaroo", "keyboard",
        "kitchen", "koala", "kind", "kaleidoscope", "landscape", "late",
        "laugh", "learning", "lemon", "letter", "lily", "magazine", "marine",
        "marshmallow", "maze", "meditate", "melody", "minute", "monument",
        "moon", "motorcycle", "mountain", "music", "north", "nose", "night",
        "name", "never", "negotiate", "number", "opposite", "octopus", "oak",
        "order", "open", "polar", "pack", "painting", "person", "picnic",
        "pillow", "pizza", "podcast", "presentation", "puppy", "puzzle",
        "recipe", "release", "restaurant", "revolve", "rewind", "room", "run",
        "secret", "seed", "ship", "shirt", "should", "small", "spaceship",
        "stargazing", "skill", "street", "style", "sunrise", "taxi", "tidy",
        "timer", "together", "tooth", "tourist", "travel", "truck", "under",
        "useful", "unicorn", "unique", "uplift", "uniform", "vase", "violin",
        "visitor", "vision", "volume", "view", "walrus", "wander", "world",
        "winter", "well", "whirlwind", "x-ray", "xylophone", "yoga", "yogurt",
        "yoyo", "you", "year", "yummy", "zebra", "zigzag", "zoology", "zone", "zeal"
    )

    suspend fun initializeDefaultWords() {
        val count = wordDao.getWordCount()
        if (count == 0) {
            defaultWords.forEach { word ->
                wordDao.insertWord(WordEntity(word = word))
            }
        }
    }

    fun getAllWords(): Flow<Set<String>> {
        return wordDao.getAllWords().map { it.toSet() }
    }

    suspend fun addWord(word: String) {
        val lowerCaseWord = word.lowercase().trim()
        if (lowerCaseWord.isNotBlank()) {
            wordDao.insertWord(WordEntity(word = lowerCaseWord))
        }
    }

    suspend fun getAllWordsOnce(): Set<String> {
        return wordDao.getAllWordsOnce().toSet()
    }
}