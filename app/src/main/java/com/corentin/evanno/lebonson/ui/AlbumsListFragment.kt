package com.corentin.evanno.lebonson.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.di.DaggerComponents
import com.corentin.evanno.lebonson.model.Album
import kotlinx.android.synthetic.main.fragment_albums_list.*
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager


class AlbumsListFragment : Fragment() {

    @Inject
    lateinit var albumsListViewModelFactory: AlbumsListViewModelFactory

    private var viewModel: AlbumListViewModel? = null

    private var adapter: AlbumsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerComponents.get().inject(this)

        viewModel = ViewModelProviders.of(this, albumsListViewModelFactory).get(AlbumListViewModel::class.java)
        viewModel?.albums()?.observe(this, Observer {
            handleData(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_albums_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albums_list.layoutManager = GridLayoutManager(context, 3)
    }

    private fun handleData(albums: List<Album>) {
        adapter = AlbumsListAdapter(albums, context)
        albums_list.adapter = adapter
    }
}