package com.corentin.evanno.lebonson

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    val backendManager = BackendManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        disposable =  backendManager.getSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            System.out.println("SUCCESS: Number of songs ${it.size}")
                        },
                        {
                            error ->
                                System.out.println("ERROR")
                        }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
