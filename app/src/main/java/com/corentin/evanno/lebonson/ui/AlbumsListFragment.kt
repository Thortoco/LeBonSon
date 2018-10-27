package com.corentin.evanno.lebonson.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.backend.BackendManager
import com.corentin.evanno.lebonson.di.DaggerApplicationComponent
import com.corentin.evanno.lebonson.di.DaggerComponents
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AlbumsListFragment : Fragment() {

    @Inject
    lateinit var backendManager: BackendManager

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerComponents.get().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_albums_list, container, false)


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

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}