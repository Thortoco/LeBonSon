package com.corentin.evanno.lebonson.di

import android.app.Application
import android.content.Context
import com.corentin.evanno.lebonson.AndroidApp
import com.corentin.evanno.lebonson.model.backend.BackendManager
import dagger.Module
import javax.inject.Singleton
import dagger.Provides



@Module
class AndroidModule {

    @Provides
    @Singleton
    fun provideContext(app: AndroidApp): Context = app
}