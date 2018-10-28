package com.corentin.evanno.lebonson

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Dao
import androidx.test.InstrumentationRegistry
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.corentin.evanno.lebonson.model.Song
import com.corentin.evanno.lebonson.model.localdb.SongDAO
import com.corentin.evanno.lebonson.model.localdb.SongDBHolder
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber

import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.test.runner.AndroidJUnit4
import androidx.room.Room
import org.junit.*


@RunWith(AndroidJUnit4::class)
class SongDaoTest {

    @JvmField @Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var songDao: SongDAO
    lateinit var db: SongDBHolder
    val song1 = Song(null, 1, 1, "aze", "1234", "1234")
    val song2 = Song(null, 1, 2, "aze", "1234", "1234")
    val song3 = Song(null, 1, 3, "aze", "1234", "1234")
    val song4 = Song(null, 2, 1, "aze", "1234", "1234")
    val songsList = listOf(song1, song2, song3, song4)

    @Before
    fun before() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, SongDBHolder::class.java).allowMainThreadQueries().build()
        songDao = db.SongDAO()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertSongsOneByOne() {
        val id = songDao.insert(song1)
        assertEquals(1, id)
        val id2 = songDao.insert(song2)
        assertEquals(2, id2)
        val id3 = songDao.insert(song3)
        assertEquals(3, id3)
        songDao.getAll().test().assertValue {
            songs -> songs.size == 3
        }
    }

    @Test
    fun insertSongsAtOnce() {
        songDao.insertSongsList(songsList)
        songDao.getAll().test().assertValue {
            songs -> songs.size == 4
        }
    }

    @Test
    fun deleteAllSongs() {
        songDao.insertSongsList(songsList)
        songDao.deleteAll()
        songDao.getAll().test().assertValue {
            songs -> songs.isEmpty()
        }
    }

    @Test
    fun selectSongsWithAlbumId() {
        songDao.insertSongsList(songsList)
        songDao.getSongsByAlbumId(1).test().assertValue {
            songs -> songs.size == 3
        }
    }
}
