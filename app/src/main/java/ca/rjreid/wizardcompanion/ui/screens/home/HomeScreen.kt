package ca.rjreid.wizardcompanion.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.medium)
            .verticalScroll(state = scrollState),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        if (uiState.resumeGameCardVisible) {
            ResumeGameCard(modifier = Modifier.fillMaxWidth()) {
                viewModel.onEvent(UiEvent.OnResumeGameClicked)
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
        PlayNowCard(modifier = Modifier.fillMaxWidth()) {
            viewModel.onEvent(UiEvent.OnNewGameClicked)
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
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