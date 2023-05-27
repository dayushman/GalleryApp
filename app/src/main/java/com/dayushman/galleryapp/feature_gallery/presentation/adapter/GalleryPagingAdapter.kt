package com.dayushman.galleryapp.feature_gallery.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dayushman.galleryapp.feature_gallery.data.model.LocalImageData
import com.dayushman.galleryapp.databinding.ImageItemLayoutBinding
import com.dayushman.galleryapp.utils.loadGlide

class GalleryPagingAdapter (val onImageClicked : ((Uri?)->Unit)?=null): PagingDataAdapter<LocalImageData, LocalImageViewHolder>(
    diffCallback
) {

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<LocalImageData>() {
            override fun areItemsTheSame(oldItem: LocalImageData, newItem: LocalImageData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LocalImageData, newItem: LocalImageData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: LocalImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalImageViewHolder {
        return LocalImageViewHolder(ImageItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false),onImageClicked)
    }
}
class LocalImageViewHolder(private val binding: ImageItemLayoutBinding,private val onImageClicked : ((Uri?)->Unit)?=null) : RecyclerView.ViewHolder(binding.root){

    fun bind(localImageViewModel: LocalImageData?){
        binding.apply {
            ivImage.loadGlide(localImageViewModel?.contentUri)
            ivImage.setOnClickListener {
                onImageClicked?.invoke(localImageViewModel?.contentUri)
            }
            tvImageName.text = localImageViewModel?.displayName
        }
    }
}
