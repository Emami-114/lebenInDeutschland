package com.emami.lebenindeutschland.domain.repo

import com.emami.lebenindeutschland.dao.Database
import com.emami.lebenindeutschland.domain.model.QuizModel
import javax.inject.Inject

class QuizRepository @Inject constructor(private val db: Database) {

    suspend fun insert(quiz: List<QuizModel>) = db.quizDao.insertDeQuiz(quiz)

    suspend fun getAll(startId: Int, endId: Int) =
        db.quizDao.getQuizRandom(startId = startId, endId = endId)

    suspend fun updateIsCorOrNot(id: String, isCorOrNot: Boolean) =
        db.quizDao.updateIsCorOrNot(id, isCorOrNot)

    suspend fun updateIsFavorite(id: String, isFavorite: Boolean) =
        db.quizDao.updateIsFavorite(id, isFavorite)


}