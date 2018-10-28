package com.corentin.evanno.lebonson.ui.albumlist

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.model.Album
import kotlinx.android.synthetic.main.fragment_albums_list.*
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager
import com.corentin.evanno.lebonson.ui.MainActivity
import com.corentin.evanno.lebonson.viewmodel.albumlist.AlbumListViewModel
import com.corentin.evanno.lebonson.viewmodel.albumlist.AlbumsListViewModelFactory
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber


class AlbumsListFragment : Fragment(), AlbumsListAdapter.Listener {

    interface Listener {
        fun startAlbumDetail(albumId: Long)
    }

    @Inject
    lateinit var albumsListViewModelFactory: AlbumsListViewModelFactory

    private var viewModel: AlbumListViewModel? = null
    private var adapter: AlbumsListAdapter? = null
    private var listener: Listener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            listener = context
        } else {
            Timber.e("Listener is not ${MainActivity::class.java}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_albums_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albums_list.layoutManager = GridLayoutManager(context, 3)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        viewModel = ViewModelProviders.of(this, albumsListViewModelFactory).get(AlbumListViewModel::class.java)
        viewModel?.albums()?.observe(this, Observer {
            handleData(it)
        })
        reload_button.setOnClickListener {
            viewModel?.fetchRemoteRepository()
            reload_button.visibility = View.INVISIBLE
            loading_progress.visibility = View.VISIBLE
            no_albums_placeholder.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }

    private fun handleData(albums: List<Album>) {
        if (albums.isEmpty()) {
            loading_progress.visibility = View.INVISIBLE
            albums_list.visibility = View.INVISIBLE
            reload_button.visibility = View.VISIBLE
            no_albums_placeholder.visibility = View.VISIBLE

        } else {
            loading_progress.visibility = View.INVISIBLE
            albums_list.visibility = View.VISIBLE
            reload_button.visibility = View.INVISIBLE
            no_albums_placeholder.visibility = View.INVISIBLE
            adapter = AlbumsListAdapter(albums, context, this)
            albums_list.adapter = adapter
        }
    }

    // region AlbumsListAdapter.Listener
    override fun onAlbumClicked(albumId: Long) {
        listener?.startAlbumDetail(albumId)
    }
    // endregion
}