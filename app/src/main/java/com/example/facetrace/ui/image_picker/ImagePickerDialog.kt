package com.example.facetrace.ui.image_picker

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.domain.model.Media
import com.example.facetrace.databinding.DialogImagePickerBinding

class ImagePickerDialog(
    context: Context,
    private val recentImages: List<Media>,
    private val openGallery: () -> Unit,
    private val openCamera: () -> Unit,
    private val chooseMedia: (media: Media) -> Unit
) : Dialog(context) {

    private val binding = DialogImagePickerBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        window?.setGravity(Gravity.BOTTOM)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bindViews()
    }

    private fun bindViews() {
        binding.rvMedia.adapter = RecentImagesAdapter.newInstance(recentImages, {
            openCamera()
            dismiss()
        }, {
            chooseMedia(it)
            dismiss()
        })
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnGallery.setOnClickListener {
            openGallery()
            dismiss()
        }
    }

}