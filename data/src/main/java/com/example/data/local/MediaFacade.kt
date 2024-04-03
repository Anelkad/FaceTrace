package com.example.data.local

import com.example.data.utils.mapTo
import com.example.domain.model.Gallery
import com.example.domain.model.Media
import com.luck.picture.lib.basic.PictureSelectionQueryModel
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnQueryDataResultListener
import javax.inject.Inject

class MediaFacade @Inject constructor() {

    private var gallery = Gallery()

    fun getRecentMedia(
        dataSource: PictureSelectionQueryModel,
        limit: Int,
        callback: (media: List<Media>) -> Unit
    ) {
        dataSource.obtainAlbumData { list ->
            if (list.isNullOrEmpty())
                callback.invoke(arrayListOf())
            else
                dataSource.obtainMediaData { media ->
                    if (list.isEmpty())
                        callback.invoke(arrayListOf())
                    else
                        callback.invoke(media.map { it.mapTo() }.take(limit))
                }
        }
    }

    fun getGallery(
        dataSource: PictureSelectionQueryModel,
        callback: (gallery: Gallery) -> Unit
    ) {
        dataSource.obtainAlbumData { list ->
            if (list.isEmpty())
                callback.invoke(Gallery())
            else {
                gallery.media = arrayListOf()
                gallery.folders = list.map { it.mapTo() }
                loadMedia(dataSource, list[0].bucketId, 1, gallery, callback)
            }
        }
    }

    fun loadMedia(
        dataSource: PictureSelectionQueryModel,
        page: Int,
        callback: (images: ArrayList<Media>) -> Unit
    ) {
        dataSource.obtainAlbumData { list ->
            dataSource.buildMediaLoader()
                .loadPageMediaData(list[0].bucketId, page, 50, 50,
                    object : OnQueryDataResultListener<LocalMedia>() {
                        override fun onComplete(
                            result: ArrayList<LocalMedia>?,
                            isHasMore: Boolean
                        ) {
                            super.onComplete(result, isHasMore)
                            if (!result.isNullOrEmpty()) {
                                result.map { it.mapTo() }.let { callback.invoke(ArrayList(it)) }
                            }
                        }
                    }
                )
        }

    }

    private fun loadMedia(
        dataSource: PictureSelectionQueryModel,
        folderId: Long,
        page: Int,
        gallery: Gallery,
        callback: (gallery: Gallery) -> Unit
    ) {
        dataSource.buildMediaLoader()
            .loadPageMediaData(folderId, page, 50, 50,
                object : OnQueryDataResultListener<LocalMedia>() {
                    override fun onComplete(
                        result: ArrayList<LocalMedia>?,
                        isHasMore: Boolean
                    ) {
                        super.onComplete(result, isHasMore)
                        result?.map { it.mapTo() }?.let { gallery.media.addAll(it) }
                        callback.invoke(gallery)
                    }
                }
            )
    }

}