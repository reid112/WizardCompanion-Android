package ca.rjreid.wizardcompanion.ui.screens.gamedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.ui.components.GameRoundDetailsCard
import ca.rjreid.wizardcompanion.ui.screens.score.WinnerCard
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing

//region Composables
@Composable
fun GameDetailsScreen(
    viewModel: GameDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val game = state.game

    when {
        state.loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.error_generic))
            }
        }
        game != null -> {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = MaterialTheme.spacing.small)
                    .verticalScroll(state = scrollState),
            ) {
                game.winner?.let {
                    WinnerCard(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                        game = game,
                        winner = it,
                        outline = true,
                        title = stringResource(id = R.string.label_game_summary)
                    )
                }
                game.rounds.forEach {
                    GameRoundDetailsCard(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                        round = it,
                        title = stringResource(id = R.string.label_round, it.number),
                        subtitle = stringResource(id = R.string.label_dealer, it.dealer.name)
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            }
        }
    }
}
//endregion

//region Previews
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WizardCompanionTheme {
        GameDetailsScreen()
    }
}
//endregion