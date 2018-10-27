package com.corentin.evanno.lebonson.di

import android.content.Context
import com.corentin.evanno.lebonson.backend.BackendManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {

    @Provides
    @Singleton
    fun provideBackendManager(): BackendManager = BackendManager()
}