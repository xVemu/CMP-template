package pl.inno4med.asystent.utils

import android.content.Context
import com.eygraber.uri.Uri
import multiplatform.network.cmptoast.toast
import org.koin.java.KoinJavaComponent

actual fun makeToast(message: String) {
    toast(
        KoinJavaComponent.get(Context::class.java),
        message,
        backgroundColor = null
    ) // backgroundColor = null means use native toast
}

actual fun getMapUrl(
    latitude: Double,
    longitude: Double,
    label: String,
) = Uri.Builder().scheme("https").authority("maps.google.com").path("/maps")
    .appendQueryParameter("q", "$latitude,$longitude($label)")
    .build()
