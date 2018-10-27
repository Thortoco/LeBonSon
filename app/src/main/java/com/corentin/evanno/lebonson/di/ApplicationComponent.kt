package com.corentin.evanno.lebonson.di

import com.corentin.evanno.lebonson.ui.AlbumsListFragment
import com.corentin.evanno.lebonson.ui.MainActivity
import dagger.Component
import javax.inject.Singleton
import dagger.BindsInstance



@Singleton
@Component(modules = [AndroidModule::class, ManagerModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(albumsListFragment: AlbumsListFragment)
}