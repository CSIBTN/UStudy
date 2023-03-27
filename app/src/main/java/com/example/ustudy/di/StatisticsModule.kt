package com.example.ustudy.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.ustudy.data.local.SharedPreferencesImpl
import com.example.ustudy.data.local.SharedPreferencesRepository
import com.example.ustudy.util.UStudyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {

    @Provides
    @Singleton
    fun providesDatastoreInstance(): DataStore<Preferences> = PreferenceDataStoreFactory.create {
        UStudyApplication.getApplicationContext().preferencesDataStoreFile("statistics")
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceRepository(dataStore: DataStore<Preferences>): SharedPreferencesRepository =
        SharedPreferencesImpl(dataStore)
}