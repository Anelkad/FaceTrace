package com.example.facetrace.base.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.utils.ActivityCompatHelper
import com.squareup.picasso.Picasso
import java.io.File

class PicassoEngine {

    fun loadGridImage(context: Context, url: String, imageView: ImageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return
        }
        val videoRequestHandler = VideoRequestHandler()
        if (PictureMimeType.isContent(url)) {
            Picasso.get()
                .load(Uri.parse(url))
                .resize(200, 200)
                .centerCrop()
                .noFade()
                .into(imageView)
        } else {
            if (PictureMimeType.isUrlHasVideo(url)) {
                val picasso: Picasso = Picasso.Builder(context.applicationContext)
                    .addRequestHandler(videoRequestHandler)
                    .build()
                picasso.load(videoRequestHandler.schemeVideo + ":" + url)
                    .resize(200, 200)
                    .centerCrop()
                    .noFade()
                    .into(imageView)
            } else {
                Picasso.get()
                    .load(File(url))
                    .resize(200, 200)
                    .centerCrop()
                    .noFade()
                    .into(imageView)
            }
        }
    }

}
