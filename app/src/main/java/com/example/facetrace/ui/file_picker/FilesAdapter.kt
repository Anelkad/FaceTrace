package com.example.facetrace.ui.file_picker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Media
import com.example.facetrace.base.extensions.loadCover
import com.example.facetrace.databinding.ItemFileBinding
import com.example.facetrace.databinding.LayoutOpenCameraBinding

class FilesAdapter(
    private val chooseMedia: (media: Media) -> Unit,
    private val openCamera: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isLoading = false
    private val items = arrayListOf<Media>()
    private val cameraType = 0
    private val images = arrayListOf(Media())

    companion object {
        fun newInstance(
            images: List<Media>,
            chooseMedia: (media: Media) -> Unit,
            openCamera: () -> Unit
        ): FilesAdapter {
            val adapter = FilesAdapter(chooseMedia, openCamera)
            adapter.images.addAll(images)
            return adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == cameraType) return CameraViewHolder(
            LayoutOpenCameraBinding.inflate(inflater, parent, false)
        )
        return AvatarViewHolder(
                ItemFileBinding.inflate(inflater, parent, false)
            )
        }

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CameraViewHolder -> holder.bind()
            is FileViewHolder -> holder.bind(images[position])
            is AvatarViewHolder -> holder.bind(images[position])
        }
    }

    override fun getItemCount(): Int = images.size

    fun addImages(images: ArrayList<Media>){
        val lastIndex = this.images.lastIndex
        this.images.addAll(images)
        notifyItemInserted(lastIndex + 1)
    }

    inner class CameraViewHolder(val binding: LayoutOpenCameraBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.root.setOnClickListener { openCamera() }
        }
    }

    inner class AvatarViewHolder(val binding: ItemFileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) {
            binding.ivFile.loadCover(media.path)
            binding.root.setOnClickListener { chooseMedia(media) }
        }
    }

    inner class FileViewHolder(val binding: ItemFileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) {
            binding.ivFile.loadCover(media.path)
            binding.root.setOnClickListener {
                chooseMedia(media)
                addMedia(media)
            }
        }

        private fun addMedia(media: Media) {
            if (items.contains(media)) {
                items.remove(media)
                notifyDataSetChanged()
            } else items.add(media)
        }
    }

}