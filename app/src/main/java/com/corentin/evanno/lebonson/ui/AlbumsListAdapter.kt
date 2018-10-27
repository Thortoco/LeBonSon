package com.corentin.evanno.lebonson.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.model.Album
import kotlinx.android.synthetic.main.album_item.view.*
import timber.log.Timber
import java.lang.NumberFormatException

class AlbumsListAdapter(private val albums: List<Album>, private val context: Context?) : RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent,false))

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.albumName.text = context?.getString(R.string.album_name, albums[position].id)

        // settings album color background using color contains in url of first song
        if (albums[position].songsList.isNotEmpty()) {
            val firstSong = albums[position].songsList[0]
            val color = "#" + firstSong.url.takeLast(6)
            try {
                holder.albumIcon.setBackgroundColor(Color.parseColor(color))
            } catch (e: NumberFormatException) {
                Timber.e("Invalid color, falling back to default")
            }
        }
        holder.albumContainer.setOnClickListener {  }
    }

    inner class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val albumName: TextView = view.album_title
        val albumIcon: ImageView = view.album_icon
        val albumContainer: CardView = view.album_container
    }
}