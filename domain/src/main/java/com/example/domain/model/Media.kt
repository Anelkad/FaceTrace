package com.example.domain.model

data class Gallery(
    var folders: List<MediaFolder> = arrayListOf(),
    var media: ArrayList<Media> = arrayListOf()
)

data class Media(
    var path: String = "",
    val folderId: Long? = null,
    val duration: String? = null,
    val mimeType: String = ""
) {
    fun getType(): MediaType {
        return if (path.contains("video"))
            MediaType.Video
        else
            MediaType.Image
    }
}

enum class MediaType {
    Image,
    Video
}

data class MediaFolder(
    val title: String,
    val id: Long
)