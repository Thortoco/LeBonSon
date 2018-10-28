package com.corentin.evanno.lebonson.model.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.corentin.evanno.lebonson.model.Song

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDBHolder : RoomDatabase() {

    abstract fun SongDAO(): SongDAO

    companion object {
        private var INSTANCE: SongDBHolder? = null

        fun getInstance(context: Context): SongDBHolder? {
            if (INSTANCE == null) {
                synchronized(SongDBHolder::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            SongDBHolder::class.java, "song.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}