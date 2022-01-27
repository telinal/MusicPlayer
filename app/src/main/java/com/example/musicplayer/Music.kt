package com.example.musicplayer

import java.io.Serializable

data class Music(val path: String, val name: String, val album: String, val artist: String): Serializable
