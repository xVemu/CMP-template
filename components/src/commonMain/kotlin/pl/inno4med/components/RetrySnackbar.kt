package pl.inno4med.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import asystent.components.generated.resources.Res
import asystent.components.generated.resources.error
import asystent.components.generated.resources.no_connection
import asystent.components.generated.resources.retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun BoxScope.RetrySnackbar(
    modifier: Modifier = Modifier,
    retry: () -> Unit,
    forceShow: Boolean,
) {
//    val hasNetwork = rememberConnectivityState(retry.takeUnless { forceShow })
    val hasNetwork = true

    Snackbar(
        modifier = modifier
            .align(Alignment.BottomCenter),
        snackbarData = object : SnackbarData {
            override val visuals = object : SnackbarVisuals {
                override val actionLabel = stringResource(Res.string.retry)
                override val duration = SnackbarDuration.Indefinite
                override val message =
                    stringResource(if (!hasNetwork) Res.string.no_connection else Res.string.error)
                override val withDismissAction = false
            }

            override fun dismiss() = Unit

            override fun performAction() {
                retry()
            }
        },
    )
}
