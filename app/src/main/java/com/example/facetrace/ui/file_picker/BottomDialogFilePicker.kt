package com.example.facetrace.ui.file_picker

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.Gallery
import com.example.domain.model.Media
import com.example.facetrace.R
import com.example.facetrace.base.extensions.hide
import com.example.facetrace.base.extensions.show
import com.example.facetrace.base.utils.PagingScrollListener
import com.example.facetrace.databinding.FragmentFilePickerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomDialogFilePicker(
    private val onCameraClick: () -> Unit,
    private val submitMedia: (media: ArrayList<Media>) -> Unit,
    private val gallery: Gallery,
    private val loadImages: ( adapter: FilesAdapter ) -> Unit
) : BottomSheetDialogFragment() {

    private var binding: FragmentFilePickerBinding? = null

    private val items = arrayListOf<Media>()

    private var buttonLayoutParams: ConstraintLayout.LayoutParams? = null
    private var collapsedMargin = 0
    private var buttonHeight = 0
    private var expandedHeight = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilePickerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { setupRatio(it as BottomSheetDialog) }
        (dialog as BottomSheetDialog).behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding?.ltFolder?.show()
                } else {
                    binding?.ltFolder?.hide()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0)
                    buttonLayoutParams?.topMargin =
                        ((expandedHeight - buttonHeight - collapsedMargin) * slideOffset + collapsedMargin).toInt()
                else
                    buttonLayoutParams?.topMargin = collapsedMargin
                binding?.actions?.layoutParams = buttonLayoutParams
            }
        })

        return dialog

    }

    private fun bindViews() {
        binding?.btnCancel?.setText(getString(R.string.cancel))
        binding?.btnCancel?.setOnClickListener { dismiss() }
        binding?.rvMedia?.layoutManager = GridLayoutManager(context, 3)
        binding?.rvMedia?.setHasFixedSize(true)
        val adapter = FilesAdapter.newInstance(
            gallery.media, { addMedia(it) }, { openCamera() }
        )
        binding?.rvMedia?.adapter = adapter
        binding?.rvMedia?.addOnScrollListener(object : PagingScrollListener(){
            override fun loadMoreItems() {
                if(!adapter.isLoading){
                    adapter.isLoading = true
                    loadImages.invoke(adapter)
                }
            }
        })
        if (gallery.folders.isNotEmpty()) {
            val menu = PopupMenu(context, binding?.ltFolder)
            gallery.folders.forEachIndexed { index, item ->
                menu.menu.add(0, index, index, item.title)
            }
            menu.setOnMenuItemClickListener { item ->
                updateInfo(item.order)
                true
            }
            binding?.ltFolder?.setOnClickListener {
                menu.show()
            }
        }
    }

    private fun updateInfo(id: Int) {
        if (gallery.folders.isEmpty()) return
        binding?.tvFolder?.text = gallery.folders[id].title
        val media = gallery.media.filter { item -> item.folderId == gallery.folders[id].id }
        binding?.rvMedia?.adapter = FilesAdapter.newInstance(
            media, { addMedia(it) }, { openCamera() }
        )
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet: FrameLayout =
            bottomSheetDialog.findViewById(
                com.google.android.material.R.id.design_bottom_sheet
            ) ?: return

        buttonLayoutParams = binding?.actions?.layoutParams as ConstraintLayout.LayoutParams

        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
        val bottomSheetLayoutParams = bottomSheet.layoutParams
        bottomSheetLayoutParams.height = getWindowHeight()

        expandedHeight = bottomSheetLayoutParams.height
        val peekHeight = (expandedHeight / 2)

        bottomSheet.layoutParams = bottomSheetLayoutParams
        BottomSheetBehavior.from(bottomSheet).skipCollapsed = false
        BottomSheetBehavior.from(bottomSheet).peekHeight = peekHeight
        BottomSheetBehavior.from(bottomSheet).isHideable = true

        buttonHeight = binding?.actions?.height?.plus(100) ?: 0

        collapsedMargin = peekHeight - buttonHeight

        buttonLayoutParams?.topMargin = collapsedMargin
        binding?.actions?.layoutParams = buttonLayoutParams

        // button margins
        val recyclerLayoutParams = binding?.rvMedia?.layoutParams as ConstraintLayout.LayoutParams
        val k = (buttonHeight - 60) / buttonHeight.toFloat()
        recyclerLayoutParams.bottomMargin = (k * buttonHeight).toInt()
        binding?.rvMedia?.layoutParams = recyclerLayoutParams

    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun addMedia(media: Media) {
        submitMedia(arrayListOf(media))
        dismiss()
    }

    private fun openCamera() {
        onCameraClick()
        dismiss()
    }

}