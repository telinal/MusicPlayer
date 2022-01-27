package com.example.musicplayer

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var getsong: ActivityResultLauncher<Intent>
    val PERMISSION_ID = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        checkPermission()
        requestPermission()

    }

    fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mainViewModel.permissionGranted.value = true
            return true
        }

        return false
    }

    fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSION_ID
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainViewModel.permissionGranted.value = true
            }
        }

    }

//    fun getSongList() {
//        if (checkPermission()) {
//            val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//            if (intent.resolveActivity(packageManager) != null) {
//                getsong.launch(intent)
//            }
//        }
//        else{
//            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
//        }
//    }
}