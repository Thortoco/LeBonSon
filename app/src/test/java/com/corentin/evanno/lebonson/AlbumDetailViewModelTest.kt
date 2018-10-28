package com.corentin.evanno.lebonson

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.corentin.evanno.lebonson.model.Song
import com.corentin.evanno.lebonson.model.localdb.SongDAO
import com.corentin.evanno.lebonson.viewmodel.albumdetail.AlbumDetailViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class AlbumDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val mockedDao = Mockito.mock(SongDAO::class.java)

    val song1 = Song(null, 1, 1, "", "", "")
    val song2 = Song(null, 1, 2, "", "", "")

    val songsList = listOf(song1, song2)
    val testScheduler = TestScheduler()

    @Test
    fun whenWeLoadSongsForAnAlbum_ThenSongsAreLoaded() {
        doReturn(Observable.just(songsList)).whenever(mockedDao)
                .getSongsByAlbumId(1)

        val albumDetailViewModel = AlbumDetailViewModel(mockedDao, testScheduler, testScheduler)
        albumDetailViewModel.loadSongs(1)

        testScheduler.triggerActions()

        verify(mockedDao).getSongsByAlbumId(1)
        Assert.assertEquals(songsList.size, albumDetailViewModel.songs().value?.size)

    }

    @Test
    fun whenSongsAreAlreadyLoaded_ThenWeReturn() {
        doReturn(Observable.just(songsList)).whenever(mockedDao)
                .getSongsByAlbumId(1)
        val albumDetailViewModel = AlbumDetailViewModel(mockedDao, testScheduler, testScheduler)
        albumDetailViewModel.loadSongs(1)

        testScheduler.triggerActions()

        albumDetailViewModel.loadSongs(1)
        verify(mockedDao, times(1)).getSongsByAlbumId(1)
    }
}