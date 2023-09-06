package com.emami.lebenindeutschland.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emami.lebenindeutschland.domain.model.QuizModel
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDeQuiz(list: List<QuizModel>)


    @Query("SELECT * FROM quiz_tab WHERE CAST (id AS INTEGER) BETWEEN :startId AND :endId  ORDER BY RANDOM()")
    suspend fun getQuizRandom(startId: Int, endId: Int, ): List<QuizModel>


    @Query("UPDATE quiz_tab SET isCorOrNot =:isCorOrNot WHERE id = :id")
    suspend fun updateIsCorOrNot(id: String, isCorOrNot: Boolean)

    @Query("UPDATE quiz_tab SET isFavors =:isFavorite WHERE id = :id")
    suspend fun updateIsFavorite(id: String, isFavorite: Boolean)


}