package com.corentin.evanno.lebonson.model.localdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.corentin.evanno.lebonson.model.Song
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SongDAO {

    @Query("SELECT * from Song")
    fun getAll(): Observable<List<Song>>

    @Query("SELECT * from Song where albumId = :albumId")
    fun getSongsByAlbumId(albumId: Long): Observable<List<Song>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: Song): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongsList(songsList: List<Song>): List<Long>

    @Query("DELETE FROM Song")
    fun deleteAll()
}