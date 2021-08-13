package com.moon.android.mondaysally.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import com.moon.android.mondaysally.R
import java.util.*

class SallyDialog(
    mContext: Context,
    private val contentText: String,
    private val btnText: String
) :
    Dialog(
        mContext
    ) {

    var listener: DialogClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_sally)
        /* Set Window */

        Objects.requireNonNull(window)!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val contentTv: TextView = findViewById(R.id.dialog_sally_content_tv)
        val btn: TextView = findViewById(R.id.dialog_sally_btn)
        btn.text = btnText
        contentTv.text = contentText
        btn.setOnClickListener {
            listener?.onOKClicked()
            dismiss()
        }
    }

    fun setOnChangeListener(listener: DialogClickListener) {
        this.listener = listener
    }

    interface DialogClickListener {
        fun onOKClicked()
    }
}