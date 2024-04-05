package com.example.facetrace.ui.upload_image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.MediaFacade
import com.example.domain.model.Gallery
import com.example.domain.model.Media
import com.example.domain.usecase.SearchImageUseCase
import com.example.facetrace.base.CommonState
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UploadImageViewModel @Inject constructor(
    @Named("MEDIA_FACADE") private val mediaFacade: MediaFacade,
    private val searchImageUseCase: SearchImageUseCase
) : ViewModel() {

    private var page = 2

    private var _state = MutableStateFlow<CommonState>(CommonState.HideLoading)
    val state: StateFlow<CommonState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = CommonState.Error(throwable.message.toString())
    }

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

    fun searchImage(path: String) {
        viewModelScope.launch(exceptionHandler) {
            _state.value = CommonState.ShowLoading
            val response = searchImageUseCase.execute(
                path = path
            )
            response.result?.let { _state.value = CommonState.Result(it) }
            response.error?.let { _state.value = CommonState.Error(it) }
            _state.value = CommonState.HideLoading
        }
    }
}