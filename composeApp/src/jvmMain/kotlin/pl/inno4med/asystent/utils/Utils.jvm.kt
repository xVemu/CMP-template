package pl.inno4med.asystent.utils

import multiplatform.network.cmptoast.showToast

actual fun makeToast(message: String) {
    showToast(message)
}
