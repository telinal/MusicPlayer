package com.example.musicplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {
     val seekbarDuration = MutableLiveData(0)
     val permissionGranted = MutableLiveData(false)
}