package com.example.lebenindeutschland.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lebenindeutschland.domain.model.QuizModel

@Database(entities = [QuizModel::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val quizDao: Dao
}