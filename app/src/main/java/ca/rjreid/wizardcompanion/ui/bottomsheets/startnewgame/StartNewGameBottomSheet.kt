package ca.rjreid.wizardcompanion.ui.bottomsheets.startnewgame

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import ca.rjreid.wizardcompanion.util.BOTTOM_BAR_HEIGHT

//region Composables
@Composable
fun StartNewGameBottomSheet(
    viewModel: StartNewGameBottomSheetViewModel = hiltViewModel(),
    onStartGameClicked: (playerNames: List<String>) -> Unit
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .verticalScroll(state = scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.label_add_player_names),
            style = MaterialTheme.typography.h6
        )
        Text(
            text = stringResource(id = R.string.label_enter_player_names),
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        uiState.players.forEachIndexed { index, player ->
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = player,
                label = {
                    Text(text = stringResource(id = R.string.hint_player, index + 1))
                },
                onValueChange = { viewModel.onEvent(UiEvent.OnPlayerUpdated(index, it)) }
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        if (uiState.players.count() < 6) {
            TextButton(onClick = { viewModel.onEvent(UiEvent.OnAddPlayerClicked) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.content_description_add_player)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
                    Text(
                        text = stringResource(id = R.string.button_add_player),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onStartGameClicked(uiState.players) }
        ) {
            Text(
                text = stringResource(id = R.string.button_start_game),
                style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = Modifier.height(BOTTOM_BAR_HEIGHT))
    }
}
//endregion

//region Previews
@Preview(showBackground = true)
@Composable
fun StartNewGameBottomSheetPreview() {
    WizardCompanionTheme {
        StartNewGameBottomSheet(onStartGameClicked = {})
    }
}
//endregion