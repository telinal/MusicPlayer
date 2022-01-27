package com.example.musicplayer

import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.musicplayer.databinding.FragmentSongListBinding
import android.content.ContentUris
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

import java.io.File
import android.widget.ArrayAdapter

import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.recyclerview.MyAdapter
import com.example.musicplayer.recyclerview.MyViewHolder
import android.R
import android.R.string
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.viewmodel.MainViewModel


class SongListFragment : Fragment() {


    lateinit var binding: FragmentSongListBinding
    private lateinit var arrayList: ArrayList<Music>
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        Log.d("songslist", "songs list: ${getAllAudioFromDevice(requireActivity())}")
        showSongsList()
        attachObservers()

    }

    private fun attachObservers() {
        mainViewModel.permissionGranted.observe(viewLifecycleOwner){ permissionGranted ->
            if (permissionGranted){
                showSongsList()
            }
        }
    }

    private fun showSongsList(){
        getAllAudioFromDevice(requireActivity())?.let {

            binding.recyclerviewList.adapter = MyAdapter(it, onItemClick = { music ->
                findNavController().navigate(SongListFragmentDirections.actionSongListFragmentToHomeFragment(music))
            })
        }
    }

    fun getAllAudioFromDevice(context: Context): List<Music>? {
        val tempAudioList: MutableList<Music> = ArrayList()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST,
        )
        val c: Cursor? = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        if (c != null) {
            while (c.moveToNext()) {

                val path = c.getString(0)
                val name = c.getString(1)
                val album = c.getString(2)
                val artist = c.getString(3)
                val audioModel = Music(path, name, album, artist)
                Log.e("Name :$name", " Album :$album")
                Log.e("Path :$path", " Artist :$artist")
                tempAudioList.add(audioModel)
            }
            c.close()
        }
        return tempAudioList
    }
}






