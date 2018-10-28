package com.corentin.evanno.lebonson.di

import android.app.Application
import com.corentin.evanno.lebonson.AndroidApp
import com.corentin.evanno.lebonson.ui.albumdetail.AlbumDetailFragment
import com.corentin.evanno.lebonson.ui.albumlist.AlbumsListFragment
import com.corentin.evanno.lebonson.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton
import dagger.android.support.AndroidSupportInjectionModule





@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AndroidModule::class, ManagerModule::class, BuildersModule::class])
interface ApplicationComponent {
    @Component.Builder
     interface Builder {
        @BindsInstance
        fun application(application: AndroidApp): Builder

        fun build(): ApplicationComponent
    }
    fun inject(androidApp: AndroidApp)
}