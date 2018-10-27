package com.corentin.evanno.lebonson.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.backend.BackendManager
import com.corentin.evanno.lebonson.di.DaggerComponents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = AlbumsListFragment()
        fragmentTransaction.add(R.id.fragment_container, fragment)
        fragmentTransaction.commit()

    }
}
