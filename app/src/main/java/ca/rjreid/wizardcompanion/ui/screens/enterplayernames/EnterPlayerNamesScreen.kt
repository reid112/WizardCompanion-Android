package ca.rjreid.wizardcompanion.ui.screens.enterplayernames

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import ca.rjreid.wizardcompanion.util.MAX_PLAYER_COUNT
import kotlinx.coroutines.flow.collect

//region Composables
@Composable
fun EnterPlayerNamesScreen(
    viewModel: EnterPlayerNamesViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
    onPopBackStack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { action ->
            when(action) {
                is Action.Navigate -> onNavigate(action.route)
                is Action.PopBackStack -> onPopBackStack()
            }
        }
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(MaterialTheme.spacing.medium)
        .verticalScroll(state = scrollState)
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
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
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
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

            if (uiState.players.count() < MAX_PLAYER_COUNT) {
                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { viewModel.onEvent(UiEvent.OnAddPlayerClicked) }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.startGameButtonEnabled,
                onClick = { viewModel.onEvent(UiEvent.OnStartGameClick) }
            ) {
                Text(
                    text = stringResource(id = R.string.button_start_game),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}
//endregion

//region Previews
@Preview(showBackground = true)
@Composable
fun EnterPlayerNamesScreenPreview() {
    WizardCompanionTheme {
        EnterPlayerNamesScreen(
            onNavigate = {},
            onPopBackStack = {}
        )
    }
}
//endregion