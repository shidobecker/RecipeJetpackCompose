package br.com.shido.recipecompose.utils

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(
    private val scope: CoroutineScope
) {
    private var snackBarJob: Job? = null

    fun getScope() = scope

    init {
        cancelActiveJob()
    }

    fun showSnackbar(scaffoldState: ScaffoldState, message: String, actionLabel: String) {
        snackBarJob = if (snackBarJob == null) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message, actionLabel)
                cancelActiveJob()//Clear everything
            }
        } else {
            cancelActiveJob() //Hide showing first
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message, actionLabel)
                cancelActiveJob()

            }
        }
    }

    private fun cancelActiveJob() {
        snackBarJob?.let { job ->
            job.cancel()
            snackBarJob = Job()
        }
    }

}