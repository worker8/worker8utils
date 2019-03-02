package com.github.worker8utilslib.common.extension

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StyleRes
import com.github.worker8utilslib.R
import kotlinx.android.synthetic.main.easy_dialog.view.*


fun Activity.makeDialog(init: AlertDialogBuilder.() -> Unit): AlertDialog {
    val builder = AlertDialogBuilder(this).apply {
        init()
    }

    val dialogBuilder = AlertDialog.Builder(this)
    val dialogView = LayoutInflater.from(this@makeDialog)
        .inflate(R.layout.easy_dialog, this@makeDialog.window.decorView as ViewGroup, false)
    dialogView.dialogTitle.text = builder.messageTitle
    dialogView.dialogMessage.text = builder.messageText

    dialogBuilder.setCancelable(builder.cancelable)
    setFinishOnTouchOutside(builder.finishOnTouchOutside)
    dialogBuilder.setView(dialogView)
    val dialog = dialogBuilder.create()

    dialogView.dialogPositiveButton.text = builder.positiveText
    dialogView.dialogPositiveButton.setOnClickListener {
        builder.positiveOnClickListener.invoke(dialog)
    }
    dialogView.dialogNegativeButton.text = builder.negativeText
    dialogView.dialogNegativeButton.setOnClickListener {
        builder.negativeOnClickListener.invoke(dialog)
    }

    return dialog
}

class AlertDialogBuilder(context: Context) {
    @StyleRes
    var themeId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        android.R.style.Theme_DeviceDefault_Dialog_Alert
    } else {
        android.R.style.Theme_Material_Dialog_Alert
    }

    var positiveText: String = ""
    var positiveOnClickListener: (Dialog) -> Unit = {}

    var negativeText: String = ""
    var negativeOnClickListener: (Dialog) -> Unit = {}

    var neutralText: String = ""
    var neutralOnClickListener: (Dialog) -> Unit = {}

    var messageTitle: String = ""
    var messageText: String = ""

    var cancelable: Boolean = true
    var finishOnTouchOutside = true
}

// doesn't support alpha
fun Int.toHtmlHexColor(): String {
    val r = Color.red(this).toString(16)
    val g = Color.green(this).toString(16)
    val b = Color.blue(this).toString(16)
    val a = Color.alpha(this).toString(16)

    return "$r$g$b"
}
