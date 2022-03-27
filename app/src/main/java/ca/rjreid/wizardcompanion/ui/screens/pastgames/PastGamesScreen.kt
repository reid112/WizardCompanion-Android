package ca.rjreid.wizardcompanion.ui.screens.pastgames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.ui.components.PastGameListItem
import ca.rjreid.wizardcompanion.ui.components.PastGamesEmptyState
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import kotlinx.coroutines.flow.collect

@Composable
fun PastGamesScreen(
    viewModel: PastGamesViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val uiState = viewModel.uiState
    val pastGames = uiState.pastGames

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { action ->
            when(action) {
                is Action.Navigate -> onNavigate(action.route)
            }
        }
    }

    when {
        uiState.noPastGames -> {
            PastGamesEmptyState(
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
        }
        !pastGames.isNullOrEmpty() -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                state = listState,
                contentPadding = PaddingValues(MaterialTheme.spacing.small)
            ) {
                items(items = pastGames) { item ->
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                    PastGameListItem(game = item, onClick = { viewModel.onEvent(UiEvent.OnGameClicked(it)) })
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                }
            }
        }
    }
}

@Preview
@Composable
fun PastGamesScreenPreview() {
    WizardCompanionTheme {
        PastGamesScreen(
            onNavigate = { }
        )
    }
}