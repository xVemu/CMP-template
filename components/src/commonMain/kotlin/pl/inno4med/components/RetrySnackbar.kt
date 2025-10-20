package pl.inno4med.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import asystent.components.generated.resources.Res
import asystent.components.generated.resources.error
import asystent.components.generated.resources.no_connection
import asystent.components.generated.resources.retry
import org.jetbrains.compose.resources.stringResource

@Composable
public fun BoxScope.RetrySnackbar(
    modifier: Modifier = Modifier,
    forceShow: Boolean = false,
    automaticRetryOnNetworkRestored: Boolean = true,
    retry: () -> Unit,
) {
    val hasNetwork = rememberNetwork(retry.takeIf { automaticRetryOnNetworkRestored })

    if (forceShow || !hasNetwork)
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

@Composable
public fun RetrySnackbarBox(forceShow: Boolean = false, retry: () -> Unit) {
    Box {
        RetrySnackbar(forceShow = forceShow, retry = retry)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun PullToRefreshAndRetrySnackbarBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    forceShowSnackbar: Boolean = false,
    automaticRetryOnNetworkRestored: Boolean = true,
    content: @Composable () -> Unit,
) {
    val state = rememberPullToRefreshState()

    Box(modifier.pullToRefresh(isRefreshing = isRefreshing, onRefresh = onRefresh, state = state)) {
        content()

        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            state = state
        )

        RetrySnackbar(
            forceShow = forceShowSnackbar,
            retry = onRefresh,
            automaticRetryOnNetworkRestored = automaticRetryOnNetworkRestored
        )
    }
}
