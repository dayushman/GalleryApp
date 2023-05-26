package com.dayushman.galleryapp.features.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.dayushman.galleryapp.R
import com.dayushman.galleryapp.features.FullscreenImageActivity
import com.dayushman.galleryapp.databinding.ActivityMainBinding
import com.dayushman.galleryapp.features.presentation.adapter.GalleryPagingAdapter
import com.dayushman.galleryapp.utils.gone
import com.dayushman.galleryapp.utils.visible
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    private lateinit var galleryPagingAdapter: GalleryPagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        if (isReadStoragePermissionGranted()) {
            setUpObserver()
        } else {
            getPermissions()
        }
    }

    private fun setUpRecyclerView() {
        galleryPagingAdapter = GalleryPagingAdapter {
            val intent = Intent(this, FullscreenImageActivity::class.java)
            intent.putExtra("imageUri", it.toString())
            startActivity(intent)
        }
        binding.rvImageList.apply {
            val flexboxLayoutManager = FlexboxLayoutManager(this.context)
            flexboxLayoutManager.flexDirection = FlexDirection.ROW
            flexboxLayoutManager.justifyContent = JustifyContent.SPACE_EVENLY
            layoutManager = flexboxLayoutManager
            adapter = galleryPagingAdapter
            itemAnimator = null
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            mainActivityViewModel.getLocalImagesPagingDataFlow().collectLatest { pagingData ->
                galleryPagingAdapter.submitData(pagingData)
            }
        }
        galleryPagingAdapter.addLoadStateListener { loadState ->
            if (loadState.prepend !is LoadState.Loading && loadState.prepend.endOfPaginationReached) {
                binding.pbLoading.gone()
                binding.rvImageList.visible()
            } else if (loadState.append !is LoadState.Loading && loadState.append.endOfPaginationReached) {
                showEmptyState()
            }
        }
    }

    private fun showEmptyState() {
        binding.apply {
            rvImageList.gone()
            pbLoading.gone()
            llEmptyOrErrorState.visible()
            ivErrorStateDrawable.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.empty_state_image))
            tvErrorText.text = getText(R.string.empty_state_text)
        }
    }

    private fun getPermissions() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { value ->
            if (value) {
                setUpObserver()
            } else {
                showErrorState()
            }
        }
        permissionLauncher.launch(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun showErrorState() {
        binding.apply {
            rvImageList.gone()
            pbLoading.gone()
            llEmptyOrErrorState.visible()
            ivErrorStateDrawable.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.error_state_image))
            tvErrorText.text = getText(R.string.no_permission_text)
        }
    }


    private fun isReadStoragePermissionGranted(): Boolean {
        val permission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
        val result = ContextCompat.checkSelfPermission(this, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}