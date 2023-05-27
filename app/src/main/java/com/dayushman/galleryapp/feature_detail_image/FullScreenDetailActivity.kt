package com.dayushman.galleryapp.feature_gallery

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dayushman.galleryapp.databinding.FullScreenDetailActivityBinding
import com.dayushman.galleryapp.utils.loadGlideWithOutTransform

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
