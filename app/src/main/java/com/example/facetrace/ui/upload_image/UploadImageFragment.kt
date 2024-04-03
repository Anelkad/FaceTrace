
package com.example.facetrace.ui.upload_image

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.example.domain.model.Gallery
import com.example.domain.model.Media
import com.example.facetrace.R
import com.example.facetrace.base.constants.Constants
import com.example.facetrace.base.extensions.loadCover
import com.example.facetrace.base.extensions.showPermissionAlert
import com.example.facetrace.databinding.FragmentUploadImageBinding
import com.example.facetrace.ui.file_picker.BottomDialogFilePicker
import com.example.facetrace.ui.image_picker.ImagePickerDialog
import com.luck.picture.lib.basic.PictureSelector
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

private const val RECENT_IMAGES_LIMIT = 10
private const val FRAGMENT_TAG = ""

@AndroidEntryPoint
class UploadImageFragment : Fragment(R.layout.fragment_upload_image) {

    private lateinit var binding: FragmentUploadImageBinding
    private val selector by lazy { PictureSelector.create(this) }
    private var currentImagePath: String? = null

    private val viewModel: UploadImageViewModel by viewModels()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                handleCameraImage()
            }
        }
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted)
            openCamera()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadImageBinding.bind(view)
        binding.btnSelectImage.setOnClickListener {
            showAvatarPicker()
        }
    }

    private fun showAvatarPicker() {
        selector?.let {
            viewModel.getRecentMedia(it, RECENT_IMAGES_LIMIT) { images ->
                ImagePickerDialog(
                    requireContext(),
                    recentImages = images,
                    { openGallery() },
                    { openCamera() },
                    { media -> chooseMedia(media) }
                ).show()
            }
        }
    }

    private fun handleCameraImage() {
        val media = currentImagePath?.let { Media(path = it) }
        media?.let { chooseCameraImage(it) }
    }

    private fun openCamera() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requireContext().showPermissionAlert(
                getString(R.string.permission_title_camera),
                getString(R.string.permission_msg_camera),
                {
                    requestCameraPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            )
        } else {
            val storageDirections = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile(
                Constants.FILE_NAME,
                Constants.IMAGE_EXTENSION,
                storageDirections
            )
            currentImagePath = imageFile.absolutePath
            val imageUri =
                FileProvider.getUriForFile(
                    requireContext(),
                    Constants.FILE_PROVIDER_AUTHORITY,
                    imageFile
                )
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            resultLauncher.launch(cameraIntent)
        }
    }

    private fun openGallery() {
        selector?.let { pictureSelector ->
            viewModel.getGallery(pictureSelector) { gallery: Gallery ->
                showFilePicker(gallery)
            }
        }
    }

    private fun showFilePicker(gallery: Gallery) {
        val filePicker = BottomDialogFilePicker(
            onCameraClick = { openCamera() },
            submitMedia = { media -> chooseMedia(media[0]) },
            gallery = gallery
        ) { adapter ->
            viewModel.getImages(selector) {
                adapter.addImages(it)
                adapter.isLoading = false
            }
        }
        if (!filePicker.isAdded)
            filePicker.show(childFragmentManager, FRAGMENT_TAG)
    }

    private fun chooseCameraImage(media: Media) {
        binding.ivSelectedImage.loadCover(media.path)
    }

    private fun chooseMedia(media: Media) {
        val srcUri = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            Uri.fromFile(File(media.path))
        else media.path.toUri()

        val realPath = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            srcUri.path
        } else {
            getRealUriPath(requireContext(), srcUri)
        }

        binding.ivSelectedImage.loadCover(media.path)
    }

    private fun getRealUriPath(
        context: Context,
        uri: Uri?,
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri!!, projection, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

}