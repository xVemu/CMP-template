package pl.inno4med.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import asystent.components.generated.resources.Res
import asystent.components.generated.resources.error
import asystent.components.generated.resources.no_connection
import asystent.components.generated.resources.no_connection_subtitle
import asystent.components.generated.resources.retry
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomError(title: StringResource? = null, withIcon: Boolean = true, retry: (() -> Unit)?) {
    val hasNetwork = rememberNetwork(onAvailable = retry)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (withIcon)
            Icon(
                painterResource(if (hasNetwork) Res.drawable.error else Res.drawable.no_connection),
                contentDescription = stringResource(if (hasNetwork) Res.string.error else Res.string.no_connection),
                modifier = Modifier.size(128.dp)
            )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(if (hasNetwork) Res.string.error else Res.string.no_connection),
            style = MaterialTheme.typography.headlineSmall
        )
        if (hasNetwork)
            retry?.let {
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = it) {
                    Text(text = stringResource(Res.string.retry).capitalize())
                }
            }
        else {
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(Res.string.no_connection_subtitle)
            )
        }
    }
}

// TODO https://youtrack.jetbrains.com/issue/KMT-1312/Preview-not-work-in-commonMain-with-multi-module
@Preview
@Composable
private fun CustomErrorPreview() {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CustomError {}
        }
    }
}
