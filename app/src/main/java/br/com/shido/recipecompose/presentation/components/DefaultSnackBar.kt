package br.com.shido.recipecompose.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    SnackbarHost(hostState = snackbarHostState,
        modifier = modifier,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = {
                        onDismiss()
                    }) {
                        Text(
                            text = snackbarHostState.currentSnackbarData?.actionLabel ?: "Hide",
                            style = TextStyle(color = Color.White)
                        )
                    }
                }) {
                Text(snackbarHostState.currentSnackbarData?.message ?: "hey")
            }
        })
}