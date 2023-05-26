package com.dayushman.galleryapp.features

import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dayushman.galleryapp.databinding.FullScreenDetailActivityBinding
import com.dayushman.galleryapp.utils.loadGlide
import com.dayushman.galleryapp.utils.loadGlideWithOutTransform
import kotlin.math.max
import kotlin.math.min

class FullscreenImageActivity : AppCompatActivity() {

    lateinit var binding: FullScreenDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FullScreenDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageUri = Uri.parse(intent.getStringExtra("imageUri"))

        binding.ivImage.loadGlideWithOutTransform(imageUri)
    }
}
