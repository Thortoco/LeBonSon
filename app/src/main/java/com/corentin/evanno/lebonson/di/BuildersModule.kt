package com.corentin.evanno.lebonson.di

import com.corentin.evanno.lebonson.ui.MainActivity
import com.corentin.evanno.lebonson.ui.albumdetail.AlbumDetailFragment
import com.corentin.evanno.lebonson.ui.albumlist.AlbumsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    internal abstract fun bindAlbumDetailFragment(): AlbumDetailFragment

    @ContributesAndroidInjector
    internal abstract fun bindAlbumListFragment(): AlbumsListFragment
}