package com.dayushman.galleryapp.feature_gallery.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.paging.LoadState
import com.dayushman.galleryapp.R
import com.dayushman.galleryapp.databinding.ActivityMainBinding
import com.dayushman.galleryapp.feature_detail_image.FullscreenImageActivity
import com.dayushman.galleryapp.feature_detail_image.FullscreenImageActivity.Companion.IMAGE_URI_TAG
import com.dayushman.galleryapp.feature_gallery.presentation.adapter.GalleryPagingAdapter
import com.dayushman.galleryapp.utils.gone
import com.dayushman.galleryapp.utils.visible
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    lateinit var galleryPagingAdapter: GalleryPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        setUpObserver()
        if (!isReadStoragePermissionGranted()) {
            getPermissions()
        }
    }

    private fun setUpRecyclerView() {
        galleryPagingAdapter = GalleryPagingAdapter {
            val intent = Intent(this, FullscreenImageActivity::class.java)
            intent.putExtra(IMAGE_URI_TAG, it.toString())
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
        mainActivityViewModel.imagesLiveData.observe(this@MainActivity) { pagingData ->
            galleryPagingAdapter.submitData(this@MainActivity.lifecycle, pagingData)
        }
        galleryPagingAdapter.addLoadStateListener { loadState ->
            if (loadState.prepend !is LoadState.Loading && loadState.prepend.endOfPaginationReached) {
                showImages()
            }
            if (loadState.append !is LoadState.Loading && loadState.append.endOfPaginationReached) {
                showEmptyState()
            }
        }
    }

    private fun showImages() {
        binding.pbLoading.gone()
        binding.llEmptyOrErrorState.gone()
        binding.rvImageList.visible()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if ((query?.length ?: 0) >= 3 || query.isNullOrEmpty()) {
                    mainActivityViewModel.searchImages(query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if ((newText?.length ?: 0) >= 3 || newText.isNullOrEmpty()) {
                    mainActivityViewModel.searchImages(newText)
                    return true
                }
                return false
            }

        })
        return true
    }

    private fun showEmptyState() {
        binding.apply {
            rvImageList.gone()
            pbLoading.gone()
            llEmptyOrErrorState.visible()
            ivErrorStateDrawable.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.empty_state_image
                )
            )
            tvErrorText.text = getText(R.string.empty_state_text)
        }
    }

    private fun getPermissions() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { value ->
            if (value) {
                mainActivityViewModel.searchImages("")
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
            ivErrorStateDrawable.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.error_state_image
                )
            )
            tvErrorText.text = getString(R.string.no_permission_text)
        }
    }


    private fun isReadStoragePermissionGranted(): Boolean {
        val permission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
        val result = ContextCompat.checkSelfPermission(this, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}