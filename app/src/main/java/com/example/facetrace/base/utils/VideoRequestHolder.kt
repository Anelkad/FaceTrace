package com.example.facetrace.base.utils

import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.text.TextUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler
import java.io.IOException

class VideoRequestHandler : RequestHandler() {
    var schemeVideo = "video"
    override fun canHandleRequest(data: Request): Boolean {
        val scheme = data.uri.scheme
        return schemeVideo == scheme
    }

    @Throws(IOException::class)
    override fun load(request: Request, networkPolicy: Int): Result? {
        val uri = request.uri
        val path = uri.path
        if (!TextUtils.isEmpty(path)) {
            val bm =
                ThumbnailUtils.createVideoThumbnail(path!!, MediaStore.Images.Thumbnails.MINI_KIND)
            return Result(bm!!, Picasso.LoadedFrom.DISK)
        }
        return null
    }
}