package pl.inno4med.asystent.utils

import com.eygraber.uri.Uri

actual fun getMapUrl(
    latitude: Double,
    longitude: Double,
    label: String,
) = Uri.Builder().scheme("https").authority("maps.google.com").path("/maps")
    .appendQueryParameter("q", "$latitude,$longitude($label)")
    .build()

actual val platform = Platform.ANDROID
