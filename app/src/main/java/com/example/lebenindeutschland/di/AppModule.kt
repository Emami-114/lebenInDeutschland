package com.example.lebenindeutschland.di

import android.app.Application
import androidx.room.Room
import com.example.lebenindeutschland.dao.Database
import com.example.lebenindeutschland.domain.repo.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuizDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app, Database::class.java, "quiz_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuizRepository(db: Database): QuizRepository {
        return QuizRepository(db)
    }


}