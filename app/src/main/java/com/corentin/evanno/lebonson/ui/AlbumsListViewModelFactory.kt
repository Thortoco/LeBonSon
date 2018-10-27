package com.corentin.evanno.lebonson.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corentin.evanno.lebonson.backend.BackendManager
import javax.inject.Inject


class AlbumsListViewModelFactory @Inject constructor(var backendManager: BackendManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(AlbumListViewModel::class.java) -> AlbumListViewModel(backendManager) as T
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    }
}