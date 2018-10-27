package com.corentin.evanno.lebonson

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class BackendManager {

    private val backendService: ILeBonSonBackend

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://img8.leboncoin.fr/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        backendService = retrofit.create<ILeBonSonBackend>(ILeBonSonBackend::class.java)
    }

    fun getSongs(): Observable<List<Song>> {
       return backendService.songsList()
    }
}