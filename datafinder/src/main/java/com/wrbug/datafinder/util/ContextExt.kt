package com.wrbug.datafinder.util

import android.app.Activity
import android.content.Context
import android.widget.Toast


fun Context.showToast(msg: String) {
    if (this is Activity) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        return
    }
    FlexibleToastUtils.show(msg)
}
