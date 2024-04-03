package com.example.facetrace.ui.image_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Media
import com.example.facetrace.R
import com.example.facetrace.base.extensions.loadCover
import com.example.facetrace.databinding.ItemMediaRecentBinding
import com.example.facetrace.databinding.LayoutOpenCameraSmallBinding

class RecentImagesAdapter(
    private val onFirstItemOnClick: () -> Unit,
    private val chooseMedia: (media: Media) -> Unit,
    private val firstImageId: Int = R.drawable.ic_media
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val images = arrayListOf(Media())

    companion object {
        fun newInstance(
            images: List<Media>,
            onFirstItemClicked: () -> Unit,
            chooseMedia: (media: Media) -> Unit,
            firstImageId: Int = R.drawable.ic_media
        ): RecentImagesAdapter {
            val adapter = RecentImagesAdapter(
                onFirstItemOnClick = onFirstItemClicked,
                chooseMedia = chooseMedia,
                firstImageId = firstImageId
            )
            adapter.images.addAll(images)
            return adapter
        }
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 0)
            return CameraViewHolder(
                LayoutOpenCameraSmallBinding.inflate(inflater, parent, false)
            )
        return ImageViewHolder(
            ItemMediaRecentBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CameraViewHolder -> holder.bind()
            is ImageViewHolder -> holder.bind(images[position])
        }
    }

    override fun getItemCount(): Int = images.size

    inner class CameraViewHolder(val binding: LayoutOpenCameraSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.ivImage.setImageResource(firstImageId)
            binding.root.setOnClickListener { onFirstItemOnClick() }
        }
    }

    inner class ImageViewHolder(val binding: ItemMediaRecentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) {
            binding.ivImage.loadCover(media.path)
            binding.root.setOnClickListener { chooseMedia(media) }
        }

    }
}