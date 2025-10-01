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
import asystent.components.generated.resources.retry
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomError(retry: (() -> Unit)? = null) {
//    val hasNetwork = rememberConnectivityState(onAvailable = retry) TODO

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(Res.drawable.error),
            contentDescription = stringResource(Res.string.error),
            modifier = Modifier.size(128.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
//            text = stringResource(if (!hasNetwork) R.string.no_connection else R.string.error),
            text = stringResource(Res.string.error),
            style = MaterialTheme.typography.headlineSmall
        )
        retry?.let {
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = it) {
                Text(text = stringResource(Res.string.retry).capitalize())
            }
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
