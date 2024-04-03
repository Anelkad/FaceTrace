package com.example.facetrace.base.extensions

import android.content.Context
import com.example.facetrace.R
import com.example.facetrace.base.view.PermissionAlert

fun Context.showPermissionAlert(
    title: String,
    message: String,
    onBtnClickAction: (() -> Unit),
    btnText: String = getString(R.string.allow),
    onDismissAction: (() -> Unit)? = null
) {
    val alert = PermissionAlert(
        this,
        title,
        message,
        btnText,
        onBtnClickAction,
        onDismissAction
    )
    alert.setCancelable(false)
    alert.show()
}