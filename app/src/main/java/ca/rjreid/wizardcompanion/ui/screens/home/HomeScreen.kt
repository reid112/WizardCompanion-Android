package ca.rjreid.wizardcompanion.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.ui.bottomsheets.startnewgame.StartNewGameBottomSheet
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//region Composables
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var bottomSheetType: BottomSheetType? by remember { mutableStateOf(null) }

    val uiState = viewModel.uiState

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { action ->
            when(action) {
                is Action.Navigate -> onNavigate(action.route)
                is Action.ShowBottomSheet -> {
                    bottomSheetType = action.sheet
                    coroutineScope.launch { bottomSheetState.show() }
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            bottomSheetType?.let { type ->
                BottomSheet(
                    type = type,
                    onStartGameClicked = { viewModel.onEvent(UiEvent.OnStartGameClicked(it)) }
                )
            }
        }
    ) {
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
            RulesCard(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }
}

@Composable
fun PlayNowCard(
    modifier: Modifier = Modifier,
    onPlayNowClick: () -> Unit
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_play_now),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_start_game),
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onPlayNowClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.button_new_game),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun ResumeGameCard(
    modifier: Modifier = Modifier,
    onResumeGameClick: () -> Unit
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_resume_game),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_game_in_progress),
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onResumeGameClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.button_resume_game),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun RulesCard(modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_rules),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Rules will go here",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun BottomSheet(
    type: BottomSheetType,
    onStartGameClicked: (playerNames: List<String>) -> Unit
) {
    when (type) {
        is BottomSheetType.StartNewGame -> {
            StartNewGameBottomSheet(onStartGameClicked = onStartGameClicked)
        }
        // Add any other types of bottom sheets here
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

@Preview(showBackground = true)
@Composable
fun PlayNowCardPreview() {
    WizardCompanionTheme {
        PlayNowCard(onPlayNowClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ResumeGameCardPreview() {
    WizardCompanionTheme {
        ResumeGameCard(onResumeGameClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun RulesCardPreview() {
    WizardCompanionTheme {
        RulesCard()
    }
}
//endregion