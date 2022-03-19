package ca.rjreid.wizardcompanion.ui.screens.score

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.domain.models.Round
import ca.rjreid.wizardcompanion.ui.components.SeparatorDot
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//region Composables
@ExperimentalPagerApi
@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { action ->
            when(action) {
                is Action.PopBackStack -> popBackStack()
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabs = listOf(
        stringResource(id = R.string.tab_title_round),
        stringResource(id = R.string.tab_title_game)
    )

    BackHandler {
        viewModel.onEvent(UiEvent.OnBackPressed)
    }

    if (uiState.leaveDialogVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(UiEvent.OnLeaveDialogCancel) },
            confirmButton = {
                TextButton(onClick = { viewModel.onEvent(UiEvent.OnLeaveDialogConfirm) }) {
                    Text(text = stringResource(id = R.string.button_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onEvent(UiEvent.OnLeaveDialogCancel) }) {
                    Text(text = stringResource(id = R.string.button_cancel))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.dialog_score_title))
            },
            text = {
                Text(text = stringResource(id = R.string.dialog_score_text))
            }
        )
    }

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPositions
                    )
                )
            },
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title) }
                )
            }
        }
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
        ) { tabIndex ->
            when (tabIndex) {
                0 -> RoundTabContent(
                    uiState = uiState,
                    onAddBidClicked = { viewModel.onEvent(UiEvent.OnAddBidClicked(it)) },
                    onAddActualClicked = { viewModel.onEvent(UiEvent.OnAddActualClicked(it)) },
                    onRemoveBidClicked = { viewModel.onEvent(UiEvent.OnRemoveBidClicked(it)) },
                    onRemoveActualClicked = { viewModel.onEvent(UiEvent.OnRemoveActualClicked(it)) },
                    onDealClicked = { viewModel.onEvent(UiEvent.OnDealClicked) },
                    onNextRoundClicked = { viewModel.onEvent(UiEvent.OnNextRoundClicked) },
                    onFinishGameClicked = { viewModel.onEvent(UiEvent.OnFinishGameClicked) },
                    onEndGameClicked = { viewModel.onEvent(UiEvent.OnEndGameClicked) }
                )
                1 -> GameTabContent(uiState.gameSummary)
            }
        }
    }
}

@Composable
fun RoundTabContent(
    uiState: UiState,
    onAddBidClicked: (PlayerBid) -> Unit,
    onAddActualClicked: (PlayerBid) -> Unit,
    onRemoveBidClicked: (PlayerBid) -> Unit,
    onRemoveActualClicked: (PlayerBid) -> Unit,
    onDealClicked: () -> Unit,
    onNextRoundClicked: () -> Unit,
    onFinishGameClicked: () -> Unit,
    onEndGameClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.medium)
            .verticalScroll(state = scrollState),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        val winner = uiState.winner
        if (winner != null) {
            WinnerCard(
                winner = winner,
                onEndGameClicked = onEndGameClicked
            )
        } else {
            RoundSummaryCard(
                uiState = uiState,
                onAddBidClicked = onAddBidClicked,
                onAddActualClicked = onAddActualClicked,
                onRemoveBidClicked = onRemoveBidClicked,
                onRemoveActualClicked = onRemoveActualClicked,
                onDealClicked = onDealClicked,
                onNextRoundClicked = onNextRoundClicked,
                onFinishGameClicked = onFinishGameClicked
            )
        }
    }
}

@Composable
fun WinnerCard(
    modifier: Modifier = Modifier,
    winner: Player,
    onEndGameClicked: () -> Unit
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_game_over),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_winner, winner.name),
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEndGameClicked() }
            ) {
                Text(
                    text = stringResource(id = R.string.button_end_game),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun RoundSummaryCard(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onAddBidClicked: (PlayerBid) -> Unit,
    onAddActualClicked: (PlayerBid) -> Unit,
    onRemoveBidClicked: (PlayerBid) -> Unit,
    onRemoveActualClicked: (PlayerBid) -> Unit,
    onDealClicked: () -> Unit,
    onNextRoundClicked: () -> Unit,
    onFinishGameClicked: () -> Unit
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_round, uiState.roundNumber),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_dealer, uiState.dealer),
                style = MaterialTheme.typography.body2
            )
            Divider(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

            uiState.bids.forEach { playerBid->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = playerBid.player.name,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                        )
                        SeparatorDot(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small))
                        Text(
                            text = playerBid.score.toString(),
                            style = MaterialTheme.typography.subtitle2,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                        )
                        if (uiState.hasDealt) {
                            SeparatorDot(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small))
                            Text(
                                text = playerBid.bid.toString(),
                                style = MaterialTheme.typography.subtitle2,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            enabled = if (uiState.hasDealt)
                                playerBid.actual > 0
                            else
                                playerBid.bid > 0,
                            onClick = {
                                if (uiState.hasDealt)
                                    onRemoveActualClicked(playerBid)
                                else
                                    onRemoveBidClicked(playerBid)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Remove,
                                contentDescription = stringResource(id = R.string.content_description_subtract)
                            )
                        }
                        Text(
                            text = if (uiState.hasDealt)
                                playerBid.actual.toString()
                            else
                                playerBid.bid.toString(),
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                        )
                        IconButton(
                            enabled = if (uiState.hasDealt)
                                uiState.bids.sumOf { it.actual } < uiState.roundNumber
                            else
                                playerBid.bid < uiState.roundNumber,
                            onClick = {
                                if (uiState.hasDealt)
                                    onAddActualClicked(playerBid)
                                else
                                    onAddBidClicked(playerBid)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(id = R.string.content_description_add),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            var callback = onDealClicked
            var buttonText = stringResource(id = R.string.button_deal)
            var enabled = true
            if (uiState.hasDealt) {
                enabled = uiState.nextRoundButtonEnabled

                if (uiState.isLastRound) {
                    callback = onFinishGameClicked
                    buttonText = stringResource(id = R.string.button_finish_game)
                } else {
                    callback = onNextRoundClicked
                    buttonText = stringResource(id = R.string.button_next_round)
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                onClick = { callback() }
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun GameTabContent(
    game: Game?
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.medium)
            .verticalScroll(state = scrollState),
    ) {
        if (game == null) {
            Text("Game is null... what??!!!?")
        } else {
            game.rounds.forEach {
                GameRoundDetailsCard(modifier = Modifier.padding(top = MaterialTheme.spacing.medium), round = it)
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        }
    }
}

@Composable
fun GameRoundDetailsCard(
    modifier: Modifier = Modifier,
    round: Round
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_round, round.number),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_dealer, round.dealer.name),
                style = MaterialTheme.typography.body2
            )
            Divider(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.label_player),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(4f)
                )
                Text(
                    text = stringResource(id = R.string.label_score),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = stringResource(id = R.string.label_bid),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = stringResource(id = R.string.label_actual),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
            }

            round.playerBids.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.player.name,
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.weight(4f)
                    )
                    Text(
                        text = it.score.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = it.bid.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = it.actual.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )
                }
            }
        }
    }
}
//endregion

//region Previews
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WizardCompanionTheme {
        ScoreScreen(
            popBackStack = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameTabContentPreview() {
    WizardCompanionTheme {
        GameTabContent(
            game = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameRoundDetailsCardPreview() {
    WizardCompanionTheme {
        GameRoundDetailsCard(
            round = Round(
                id = 0,
                number = 3,
                dealer = Player(0, "Riley"),
                playerBids = listOf()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoundSummaryCardPreview() {
    WizardCompanionTheme {
        RoundSummaryCard(
            uiState = UiState(),
            onAddBidClicked = { },
            onAddActualClicked = { },
            onRemoveBidClicked = { },
            onRemoveActualClicked = { },
            onDealClicked = { },
            onNextRoundClicked = { },
            onFinishGameClicked = { }
        )
    }
}
//endregion