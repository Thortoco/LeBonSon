package com.corentin.evanno.lebonson.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Song")
data class Song(@PrimaryKey(autoGenerate = true) var primaryKey: Long?,
                @ColumnInfo(name = "albumId") val albumId: Long,
                @ColumnInfo(name = "songId") val id: Long,
                @ColumnInfo(name = "title") val title: String,
                @ColumnInfo(name = "url") val url: String,
                @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String)