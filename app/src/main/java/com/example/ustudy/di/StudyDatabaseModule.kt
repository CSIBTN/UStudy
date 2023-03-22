package com.example.ustudy.di

import android.content.Context
import androidx.room.Room
import com.example.ustudy.data.local.StudyDatabase
import com.example.ustudy.util.UStudyApplication
import com.example.ustudy.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StudyDatabaseModule {

    @Provides
    fun providesDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        UStudyApplication.getApplicationContext(),
        StudyDatabase::class.java,
        Util.DATABASE_NAME
    ).build()

}