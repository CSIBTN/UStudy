package com.example.ustudy.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ustudy.data.local.StudyDatabase
import com.example.ustudy.data.local.StudyImpl
import com.example.ustudy.data.local.StudyRepository
import com.example.ustudy.util.UStudyApplication
import com.example.ustudy.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudyDatabaseModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE `task` (`id` INTEGER NOT NULL, `day` INTEGER NOT NULL, `month` TEXT NOT NULL, `year` INTEGER NOT NULL, `task` TEXT NOT NULL, " + "PRIMARY KEY(`id`))"
            )
        }
    }
    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DROP TABLE `task`")
            database.execSQL(
                "CREATE TABLE `task` (`id` INTEGER NOT NULL, `day` INTEGER NOT NULL, `month` TEXT NOT NULL, `year` INTEGER NOT NULL, `task` TEXT NOT NULL, " + "PRIMARY KEY(`id`))"
            )
        }
    }


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        UStudyApplication.getApplicationContext(), StudyDatabase::class.java, Util.DATABASE_NAME
    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()


    @Provides
    @Singleton
    fun provideDatabaseRepository(studyDatabase: StudyDatabase): StudyRepository =
        StudyImpl(studyDatabase)

}