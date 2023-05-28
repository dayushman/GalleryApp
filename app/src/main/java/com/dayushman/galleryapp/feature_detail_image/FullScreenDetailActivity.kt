package com.dayushman.galleryapp.feature_detail_image

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dayushman.galleryapp.databinding.FullScreenDetailActivityBinding
import com.dayushman.galleryapp.utils.loadGlideWithOutTransform

class FullscreenImageActivity : AppCompatActivity() {

    lateinit var binding: FullScreenDetailActivityBinding
    companion object{
        const val IMAGE_URI_TAG = "imageUri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FullScreenDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(IMAGE_URI_TAG))
        binding.ivImage.loadGlideWithOutTransform(imageUri)
    }
}
