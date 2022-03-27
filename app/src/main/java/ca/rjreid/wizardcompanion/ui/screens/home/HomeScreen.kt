package ca.rjreid.wizardcompanion.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.ui.components.PlayNowCard
import ca.rjreid.wizardcompanion.ui.components.ResumeGameCard
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import kotlinx.coroutines.flow.collect

//region Composables
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { action ->
            when(action) {
                is Action.Navigate -> onNavigate(action.route)
            }
        }
    }

    if (uiState.startGameDialogVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(UiEvent.OnStartGameDialogCancel) },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(UiEvent.OnStartGameDialogConfirm) }) {
                    Text(text = stringResource(id = R.string.button_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onEvent(UiEvent.OnStartGameDialogCancel) }) {
                    Text(text = stringResource(id = R.string.button_cancel))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.dialog_game_in_progress_title))
            },
            text = {
                Text(text = stringResource(id = R.string.dialog_game_in_progress_text))
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.small)
            .verticalScroll(state = scrollState),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        if (uiState.resumeGameCardVisible) {
            ResumeGameCard(modifier = Modifier.fillMaxWidth()) {
                viewModel.onEvent(UiEvent.OnResumeGameClicked)
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        }
        PlayNowCard(modifier = Modifier.fillMaxWidth()) {
            viewModel.onEvent(UiEvent.OnNewGameClicked)
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
    }
}
//endregion

//region Previews
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WizardCompanionTheme {
        HomeScreen(onNavigate = {})
    }
}
//endregion