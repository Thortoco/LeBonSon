package com.corentin.evanno.lebonson.ui.albumdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.model.Album
import com.corentin.evanno.lebonson.model.Song
import com.corentin.evanno.lebonson.ui.MainActivity
import com.corentin.evanno.lebonson.viewmodel.albumdetail.AlbumDetailViewModel
import com.corentin.evanno.lebonson.viewmodel.albumdetail.AlbumDetailViewModelFactory
import com.corentin.evanno.lebonson.viewmodel.albumlist.AlbumListViewModel
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_albums_list.*
import timber.log.Timber
import javax.inject.Inject
import androidx.appcompat.app.AppCompatActivity



class AlbumDetailFragment : Fragment() {

    companion object {
        const val ALBUM_DETAIl_KEY = "ALBUM_DETAIL_KEY"
    }

    @Inject
    lateinit var albumDetailViewModelFactory: AlbumDetailViewModelFactory

    private var viewModel: AlbumDetailViewModel? = null
    private var adapter: AlbumDetailAdapter? = null
    private var albumId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        albumId = arguments?.getLong(ALBUM_DETAIl_KEY)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_album_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle()
        albums_list.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProviders.of(this, albumDetailViewModelFactory).get(AlbumDetailViewModel::class.java)
        viewModel?.loadSongs(albumId ?: -1)
        viewModel?.songs()?.observe(this, Observer {
            handleData(it)
        })
    }

    private fun handleData(songsList: List<Song>) {
        adapter = AlbumDetailAdapter(songsList)
        albums_list.adapter = adapter
    }

    private fun setToolbarTitle() {
        val albumId = albumId?.toString()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.album_number, albumId)
    }
}