package com.corentin.evanno.lebonson.viewmodel.albumlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corentin.evanno.lebonson.model.backend.BackendManager
import com.corentin.evanno.lebonson.model.localdb.SongDBHolder
import javax.inject.Inject


class AlbumsListViewModelFactory @Inject constructor(var backendManager: BackendManager, var context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(AlbumListViewModel::class.java) ->
            AlbumListViewModel(backendManager, SongDBHolder.getInstance(context)?.SongDAO()) as T
        else ->
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}