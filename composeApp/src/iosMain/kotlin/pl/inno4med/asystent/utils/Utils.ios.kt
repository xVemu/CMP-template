package pl.inno4med.asystent.utils

import com.eygraber.uri.Uri
import multiplatform.network.cmptoast.showToast

actual fun makeToast(message: String) {
    showToast(message)
}

actual fun getMapUrl(
    latitude: Double,
    longitude: Double,
    label: String,
) = Uri.Builder().scheme("https").authority("maps.apple.com").path("/maps")
    .appendQueryParameter("ll", "$latitude,$longitude")
    .appendQueryParameter("q", label)
    .build()
