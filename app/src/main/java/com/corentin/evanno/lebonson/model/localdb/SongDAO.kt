package com.corentin.evanno.lebonson.model.localdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    fun insert(song: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongsList(songsList: List<Song>)
}