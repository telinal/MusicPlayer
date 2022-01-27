package com.example.musicplayer

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.viewmodel.MainViewModel


class PlayFragment : Fragment() {

    private var bindingObj: FragmentHomeBinding? = null
    private lateinit var binding: FragmentHomeBinding
    lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var mainViewModel: MainViewModel
    private var mediaPlayer: MediaPlayer? = null
    private val navArgs: PlayFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (bindingObj == null){
            bindingObj = FragmentHomeBinding.inflate(inflater, container, false)
            binding = bindingObj!!
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        if (mediaPlayer == null){
             mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(navArgs.music.path))
        }
        binding.playlistBtn.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.songTitle.text = navArgs.music.name

        mediaPlayer!!.start()
        binding.playBtn.setImageResource(R.drawable.ic_pause)

        binding.seekBar.progress = mainViewModel.seekbarDuration.value!!


        binding.seekBar.max = mediaPlayer!!.duration
        binding.playBtn.setOnClickListener{
            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
                binding.playBtn.setImageResource(R.drawable.ic_pause)
            }else{
                mediaPlayer!!.pause()
                binding.playBtn.setImageResource(R.drawable.ic_play)

            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, pos: Int, changed: Boolean) {
                if (changed) {
                    mediaPlayer!!.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        runnable = Runnable {
            binding.seekBar.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        mediaPlayer!!.setOnCompletionListener {
            binding.playBtn.setImageResource(R.drawable.ic_play)
            binding.seekBar.progress = 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}



