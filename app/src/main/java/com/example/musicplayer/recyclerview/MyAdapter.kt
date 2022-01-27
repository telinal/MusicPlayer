package com.example.musicplayer.recyclerview

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Music
import com.example.musicplayer.R

class MyAdapter(private val songs: List<Music>, private val onItemClick:  (Music) -> Unit) : RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = songs[position]
//        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.title.text = currentItem.name
        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentItem)
        }


    }

    override fun getItemCount(): Int {
        return songs.size
    }


}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleImage = itemView.findViewById<ImageView>(R.id.songImage)
    val title = itemView.findViewById<TextView>(R.id.songTitle)
//    val btn = itemView.findViewById<ImageButton>(R.id.songAction)
}
