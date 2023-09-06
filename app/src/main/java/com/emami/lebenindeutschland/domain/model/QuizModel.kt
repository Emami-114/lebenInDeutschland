package com.emami.lebenindeutschland.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quiz_tab")
data class QuizModel(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val question: String = "",
    val corAnswer: String = "",
    var answer1: String = "",
    var answer2: String = "",
    var answer3: String = "",
    var url: Int? = null,
    var isCorOrNot: Boolean? = null,
    var isFavors: Boolean? = null,
)
