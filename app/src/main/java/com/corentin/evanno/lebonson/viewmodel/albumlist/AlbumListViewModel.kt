package com.corentin.evanno.lebonson.viewmodel.albumlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corentin.evanno.lebonson.model.backend.BackendManager
import com.corentin.evanno.lebonson.model.Album
import com.corentin.evanno.lebonson.model.Song
import com.corentin.evanno.lebonson.model.backend.IBackendManager
import com.corentin.evanno.lebonson.model.localdb.SongDAO
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AlbumListViewModel(var backendManager: IBackendManager, var dao: SongDAO?,
                         var androidScheduler: Scheduler, var processScheduler: Scheduler) : ViewModel() {

    private var disposable: Disposable? = null
    private val albums: MutableLiveData<List<Album>> = MutableLiveData()

    fun albums(): MutableLiveData<List<Album>> = albums

    init {
        fetchLocalRepository()
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    private fun fetchLocalRepository() {
        disposable = dao?.getAll()
                ?.subscribeOn(processScheduler)
                ?.observeOn(androidScheduler)
                ?.subscribe({ songs ->
                        if (songs.isEmpty()) {
                            fetchRemoteRepository()
                        } else {
                            albums.value = mapSongsToAlbum(songs)
                        }
                },
                        {
                            Timber.e("Error retrieving songs in db")
                            fetchRemoteRepository()
                        }

                )
    }

    fun fetchRemoteRepository() {
        disposable = backendManager.getSongs()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(
                        { songs ->
                            Timber.d("Success Number of songs ${songs.size}")
                            // wait for https://issuetracker.google.com/issues/63317956 to use rx instead of runnable
                             Thread( Runnable {
                                 dao?.insertSongsList(songs)
                             }) .start()
                            albums.value = mapSongsToAlbum(songs)
                        },
                        { error ->
                            Timber.e("Error No data fetched => ${error.localizedMessage}")
                            albums.value = arrayListOf()
                        }
                )
    }

    private fun mapSongsToAlbum(songs: List<Song>): List<Album> {
        return songs
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
    }
}