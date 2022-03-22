package ca.rjreid.wizardcompanion.ui.screens.pastgames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.domain.models.Round
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import java.util.*

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
                .background(MaterialTheme.colors.background)
                .padding(horizontal = MaterialTheme.spacing.medium),
            state = listState
        ) {
            items(items = it) { item ->
                PastGameListItem(game = item)
            }
        }
    } ?: PastGamesEmptyState()
}

@Composable
fun PastGameListItem(
    modifier: Modifier = Modifier,
    game: Game
) {
    val playerNames = game.players.joinToString(", ") { it.name }
    Column(modifier = modifier) {
        Text(
            text = Date(game.date.time).toString(),
            style = MaterialTheme.typography.overline
        )
        Text(
            text = stringResource(R.string.label_winner, game.winner?.name ?: "Unknown"),
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = stringResource(R.string.label_players, playerNames)
        )
    }
}

@Composable
fun PastGamesEmptyState() {
    Text(text = "Empty (todo)")
}

@Composable
@Preview(showBackground = true)
fun PastGameListItemPreview() {
    WizardCompanionTheme {
        val riley = Player(1, "Riley")
        val brittani = Player(2, "Brittani")
        val dave = Player(3, "Dave")

        val round1 = Round(1, 1, riley, playerBids = listOf(
            PlayerBid(1, riley, 0, 0, 0)
        ))

        PastGameListItem(game = Game(
            id = 1,
            date = Date(),
            players = listOf(riley, brittani, dave),
            rounds = listOf(round1),
            winner = riley
        ))
    }
}

@Composable
@Preview(showBackground = true)
fun PastGamesEmptyStatePreview() {
    WizardCompanionTheme {
        PastGamesEmptyState()
    }
}