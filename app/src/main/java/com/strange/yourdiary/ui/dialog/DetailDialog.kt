package com.strange.yourdiary.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.strange.yourdiary.R
import kotlinx.android.synthetic.main.dialog_diary_detail.*

class DetailDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_diary_detail)

        tv_dialog_close.setOnClickListener {
            dismiss()
        }

    }

}