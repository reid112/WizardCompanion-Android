package ca.rjreid.wizardcompanion.ui.screens.pastgames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.ui.components.pastgameslist.PastGameListItem
import ca.rjreid.wizardcompanion.ui.components.pastgameslist.PastGamesEmptyState
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing

@Composable
fun PastGamesScreen(
    viewModel: PastGamesViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val uiState = viewModel.uiState

    uiState.pastGames?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            state = listState,
            contentPadding = PaddingValues(MaterialTheme.spacing.medium)
        ) {
            items(items = it) { item ->
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                PastGameListItem(game = item)
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            }
        }
    } ?: PastGamesEmptyState(
        modifier = Modifier.padding(MaterialTheme.spacing.medium)
    )
}

@Preview
@Composable
fun PastGamesScreenPreview() {
    WizardCompanionTheme {
        PastGamesScreen()
    }
}