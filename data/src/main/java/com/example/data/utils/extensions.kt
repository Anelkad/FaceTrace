package com.example.data.utils

import com.example.domain.model.Media
import com.example.domain.model.MediaFolder
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.entity.LocalMediaFolder
import com.luck.picture.lib.utils.DateUtils

fun LocalMedia.mapTo() = Media(
    path = this.path,
    folderId = this.bucketId,
    duration = DateUtils.formatDurationTime(this.duration),
    mimeType = this.mimeType
)

fun LocalMediaFolder.mapTo() = MediaFolder(
    title = this.folderName,
    id = this.bucketId
)