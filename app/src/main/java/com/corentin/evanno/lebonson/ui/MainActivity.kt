package com.corentin.evanno.lebonson.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.ui.albumdetail.AlbumDetailFragment
import com.corentin.evanno.lebonson.ui.albumdetail.AlbumDetailFragment.Companion.ALBUM_DETAIl_KEY
import com.corentin.evanno.lebonson.ui.albumlist.AlbumsListFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(), AlbumsListFragment.Listener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = AlbumsListFragment()
            fragmentTransaction.add(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun startAlbumDetail(albumId: Long) {
        val bundle = Bundle()
        bundle.putLong(ALBUM_DETAIl_KEY, albumId)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = AlbumDetailFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(fragment.javaClass.name)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}
