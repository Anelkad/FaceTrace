package com.example.facetrace.base.utils

import androidx.recyclerview.widget.RecyclerView

abstract class PagingScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE ||
            newState == RecyclerView.SCROLL_STATE_DRAGGING
        ) {
            loadMoreItems()
        }
    }

    protected abstract fun loadMoreItems()

}