package pl.inno4med.asystent.utils

import android.content.Context
import multiplatform.network.cmptoast.toast
import org.koin.java.KoinJavaComponent

actual fun makeToast(message: String) {
    toast(
        KoinJavaComponent.get(Context::class.java),
        message,
        backgroundColor = null
    ) // backgroundColor = null means use native toast
}
