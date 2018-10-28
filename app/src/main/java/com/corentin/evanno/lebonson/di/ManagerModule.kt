package com.corentin.evanno.lebonson.di

import com.corentin.evanno.lebonson.model.backend.BackendManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {

    @Provides
    @Singleton
    fun provideBackendManager(): BackendManager = BackendManager()
}