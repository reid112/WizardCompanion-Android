package ca.rjreid.wizardcompanion.ui.components.pastgameslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.domain.models.Round
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import java.util.*

@Composable
fun PastGameListItem(
    modifier: Modifier = Modifier,
    game: Game
) {
    val playerNames = game.players.joinToString(", ") { it.name }
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
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