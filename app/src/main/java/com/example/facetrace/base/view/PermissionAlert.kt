package com.example.facetrace.base.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.facetrace.databinding.AlertPermissionBinding

class PermissionAlert(
    context: Context,
    private val title: String,
    private val message: String,
    private val btnText: String,
    private val onBtnClickAction: (() -> Unit),
    private val onDismissAction: (() -> Unit)? = null
) : Dialog(context) {

    private val binding = AlertPermissionBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.tvTitle.text = title
        binding.tvMessage.text = message
        binding.btnAllow.text = btnText
        binding.btnAllow.setOnClickListener {
            onBtnClickAction.invoke()
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            onDismissAction?.invoke()
            dismiss()
        }
    }

}