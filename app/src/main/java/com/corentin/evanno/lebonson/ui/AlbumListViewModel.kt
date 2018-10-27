package com.corentin.evanno.lebonson.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corentin.evanno.lebonson.backend.BackendManager
import com.corentin.evanno.lebonson.model.Album
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AlbumListViewModel @Inject constructor(var backendManager: BackendManager) : ViewModel() {

    private var disposable: Disposable? = null
    private val albums: MutableLiveData<List<Album>> = MutableLiveData()

    fun albums(): MutableLiveData<List<Album>> = albums

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() {
        if (!fetchLocalRepository()) {
            fetchRemoteRepository()
        }
    }

    private fun fetchLocalRepository(): Boolean {
        // todo fetch local repo
        return false
    }

    private fun fetchRemoteRepository() {
        disposable = backendManager.getSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { songs ->
                            Timber.d("SUCCESS: Number of songs ${songs.size}")
                            // todo save to db

                            albums.value = songs
                                    .asSequence()
                                    .map { it.albumId }
                                    .distinct()
                                    .map { albumId ->
                                        val songsOfAlbum = songs
                                                .asSequence()
                                                .map { it }
                                                .filter { it.albumId == albumId }
                                                .toList()
                                        Album(albumId, songsOfAlbum)
                                    }
                                    .toList()
                        },
                        { error ->
                            Timber.e("ERROR: No data fetched => ${error.localizedMessage}")
                            albums.value = null
                        }
                )
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}