package com.corentin.evanno.lebonson

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.corentin.evanno.lebonson.model.Album
import com.corentin.evanno.lebonson.model.Song
import com.corentin.evanno.lebonson.model.backend.IBackendManager
import com.corentin.evanno.lebonson.model.localdb.SongDAO
import com.corentin.evanno.lebonson.viewmodel.albumlist.AlbumListViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Test

import org.mockito.Mockito
import org.junit.rules.TestRule
import org.junit.Rule



class AlbumListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val mockedDao = Mockito.mock(SongDAO::class.java)
    val mockedBackendManager = Mockito.mock(IBackendManager::class.java)

    val song1 = Song(null, 1, 1, "", "", "")
    val song2 = Song(null, 1, 2, "", "", "")

    val songsList = listOf(song1, song2)
    val testScheduler = TestScheduler()

    @Test
    fun onInit_ThenWeFetchLocalRepository() {
        doReturn(Observable.just(songsList)).whenever(mockedDao)
                .getAll()
        val albumListViewModel = AlbumListViewModel(mockedBackendManager, mockedDao, testScheduler, testScheduler)
        testScheduler.triggerActions()

        verify(mockedDao).getAll()
        val albumList = mapSongsToAlbum(songsList)
        verify(mockedBackendManager, never()).getSongs()
        Assert.assertEquals(albumList.size, albumListViewModel.albums().value?.size)
    }

    @Test
    fun whenWeWantToReloadDataFromRemote_ThenWeGetSongs() {
        doReturn(Observable.just(songsList)).whenever(mockedBackendManager)
                .getSongs()
        val albumListViewModel = AlbumListViewModel(mockedBackendManager, mockedDao, testScheduler, testScheduler)

        albumListViewModel.fetchRemoteRepository()
        testScheduler.triggerActions()

        verify(mockedBackendManager).getSongs()

        val albumList = mapSongsToAlbum(songsList)
        Assert.assertEquals(albumList.size, albumListViewModel.albums().value?.size)

    }

    @Test
    fun whenWeFailToRetrieveFromLocalRepository_ThenWeLoadFromRemote() {
        doReturn(Observable.just(listOf<Song>())).whenever(mockedDao)
                .getAll()
        doReturn(Observable.just(songsList)).whenever(mockedBackendManager)
                .getSongs()
        val albumListViewModel = AlbumListViewModel(mockedBackendManager, mockedDao, testScheduler, testScheduler)

        testScheduler.triggerActions()
        verify(mockedBackendManager).getSongs()
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
