package com.corentin.evanno.lebonson.viewmodel.albumdetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corentin.evanno.lebonson.model.Album
import com.corentin.evanno.lebonson.model.Song
import com.corentin.evanno.lebonson.model.localdb.SongDAO
import com.corentin.evanno.lebonson.model.localdb.SongDBHolder
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AlbumDetailViewModel(var dao: SongDAO?, var androidScheduler: Scheduler,
                           var processScheduler: Scheduler) : ViewModel() {

    private var disposable: Disposable? = null
    private val songs: MutableLiveData<List<Song>> = MutableLiveData()

    fun songs(): MutableLiveData<List<Song>> = songs

    fun loadSongs(albumId: Long) {
        songs.value?.let {
            if (it.isNotEmpty()) {
                Timber.d("Songs already loaded, no need to refresh view")
                return
            }
        }
        fetchLocalRepository(albumId)
    }

    private fun fetchLocalRepository(albumId: Long) {
        disposable = dao?.getSongsByAlbumId(albumId)
                ?.subscribeOn(processScheduler)
                ?.observeOn(androidScheduler)
                ?.subscribe( {
                    songsList ->
                    songs.value = songsList
                },
                        {
                            Timber.e("Error retrieveing songs")
                        }
                )
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}