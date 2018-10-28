package com.corentin.evanno.lebonson.ui.albumdetail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.corentin.evanno.lebonson.R
import com.corentin.evanno.lebonson.model.Song
import kotlinx.android.synthetic.main.song_item.view.*
import timber.log.Timber

class AlbumDetailAdapter(private val songs: List<Song>)
    : RecyclerView.Adapter<AlbumDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent,false))

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songName.text = songs[position].title

        val color = "#" + songs[position].url.takeLast(6)
        try {
            holder.songIcon.setBackgroundColor(Color.parseColor(color))
        } catch (e: NumberFormatException) {
            Timber.e("Invalid color, falling back to default")
        }
    }

    inner class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val songName: TextView = view.song_title
        val songIcon: ImageView = view.song_icon
    }
}