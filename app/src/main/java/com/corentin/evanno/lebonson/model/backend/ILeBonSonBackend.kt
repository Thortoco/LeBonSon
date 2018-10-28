package com.corentin.evanno.lebonson.model.backend

import com.corentin.evanno.lebonson.model.Song
import io.reactivex.Observable
import retrofit2.http.GET

interface ILeBonSonBackend {
    @GET("technical-test.json")
    fun songsList(): Observable<List<Song>>
}