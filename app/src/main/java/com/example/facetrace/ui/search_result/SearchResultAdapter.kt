package com.example.facetrace.ui.search_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.SearchResult
import com.example.facetrace.databinding.ItemMediaBinding

class SearchResultAdapter(
    private val images: List<SearchResult>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(
            ItemMediaBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(images[position])
        }
    }

    override fun getItemCount(): Int = images.size

    inner class ImageViewHolder(val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resultItem: SearchResult) {
            Glide
                .with(binding.root.context)
                .load(resultItem.url)
                .into(binding.ivImage)
        }
    }
}