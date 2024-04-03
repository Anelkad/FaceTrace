package com.example.facetrace.ui.upload_image

import androidx.lifecycle.ViewModel
import com.example.data.local.MediaFacade
import com.example.domain.model.Gallery
import com.example.domain.model.Media
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UploadImageViewModel @Inject constructor(
    @Named("MEDIA_FACADE") private val mediaFacade: MediaFacade,
) : ViewModel() {

    private var page = 2

    fun getRecentMedia(
        selector: PictureSelector,
        limit: Int,
        callback: (media: List<Media>) -> Unit
    ) {
        mediaFacade.getRecentMedia(
            selector.dataSource(SelectMimeType.ofImage()),
            limit,
            callback
        )
    }

    fun getGallery(
        selector: PictureSelector,
        callback: (gallery: Gallery) -> Unit
    ) {
        page = 2
        mediaFacade.getGallery(
            selector.dataSource(SelectMimeType.ofImage()),
            callback
        )
    }

    fun getImages(
        selector: PictureSelector,
        callback: (images: ArrayList<Media>) -> Unit
    ) {
        mediaFacade.loadMedia(selector.dataSource(SelectMimeType.ofImage()), page++, callback)
    }

}