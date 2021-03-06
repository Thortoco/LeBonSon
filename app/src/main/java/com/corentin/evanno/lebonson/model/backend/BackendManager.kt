package com.corentin.evanno.lebonson.model.backend

import com.corentin.evanno.lebonson.model.Song
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface IBackendManager {
    fun getSongs(): Observable<List<Song>>
}

class BackendManager: IBackendManager {

    private val backendService: ILeBonSonBackend

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://img8.leboncoin.fr/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        backendService = retrofit.create<ILeBonSonBackend>(ILeBonSonBackend::class.java)
    }

    override fun getSongs(): Observable<List<Song>> {
       return backendService.songsList()
    }
}