package com.example.facetrace.base.extensions

import android.widget.ImageView
import com.example.facetrace.base.utils.PicassoEngine

fun ImageView.loadCover(path: String) {
    PicassoEngine().loadGridImage(
        context,
        path,
        this
    )
}