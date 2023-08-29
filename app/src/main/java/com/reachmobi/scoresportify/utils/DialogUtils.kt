package com.reachmobi.scoresportify.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.reachmobi.scoresportify.R
import com.reachmobi.scoresportify.databinding.DialogMessageBinding

object DialogUtils {
    fun showMessage(
        context: Context?,
        title: String?,
        message: String?,
        onClick: OnClick?
    ) {
        if (context == null) return
        val dialog = AlertDialog.Builder(context).create()
        val b: DialogMessageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_message,
            null,
            false
        )
        dialog.setView(b.root)
        b.imgClose.setOnClickListener { dialog.dismiss() }
        b.tvTitle.text = title
        b.tvMessage.text = message
        b.btnOk.setOnClickListener {
            onClick?.onClick()
            dialog.dismiss()
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }

    interface OnClick {
        fun onClick()
    }
}