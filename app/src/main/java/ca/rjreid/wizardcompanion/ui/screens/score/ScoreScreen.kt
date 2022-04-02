package ca.rjreid.wizardcompanion.ui.screens.score

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.ui.components.GameRoundDetailsCard
import ca.rjreid.wizardcompanion.ui.components.SeparatorDot
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import ca.rjreid.wizardcompanion.util.game1
import ca.rjreid.wizardcompanion.util.player1
import ca.rjreid.wizardcompanion.util.playerBids
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
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabs = listOf(
        stringResource(id = R.string.tab_title_round),
        stringResource(id = R.string.tab_title_game)
    )

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { action ->
            when(action) {
                is Action.PopBackStack -> popBackStack()
                is Action.ShowGameTab -> pagerState.scrollToPage(1)
            }
        }
    }

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

    Column(modifier = Modifier.fillMaxSize()) {
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
            modifier = Modifier.fillMaxSize(),
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
                    onEndGameClicked = { viewModel.onEvent(UiEvent.OnEndGameClicked) },
                    onGoToGameDetailsClicked = { viewModel.onEvent(UiEvent.OnGoToGameDetailsClicked) }
                )
                1 -> GameTabContent(uiState)
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
    onEndGameClicked: () -> Unit,
    onGoToGameDetailsClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.small)
            .verticalScroll(state = scrollState),
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

        val game = uiState.gameSummary
        val winner = uiState.winner
        if (game != null && winner != null) {
            WinnerCard(
                game = game,
                winner = winner,
                title = stringResource(id = R.string.label_game_over),
                onEndGameClicked = onEndGameClicked,
                onGoToGameDetailsClicked = onGoToGameDetailsClicked
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
    game: Game,
    winner: Player,
    title: String,
    outline: Boolean = false,
    onGoToGameDetailsClicked: (() -> Unit)? = null,
    onEndGameClicked: (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        val m = if (outline)
            Modifier.border(BorderStroke(2.dp, MaterialTheme.colors.secondary), shape = MaterialTheme.shapes.medium)
        else
            Modifier

        GameRoundDetailsCard(
            modifier = m,
            round = game.rounds.last(),
            title = title,
            subtitle = stringResource(id = R.string.label_winner, winner.name)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

        Column(modifier = Modifier.fillMaxWidth()) {
            onGoToGameDetailsClicked?.let {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { it() }
                ) {
                    Text(
                        text = stringResource(id = R.string.button_game_details),
                        style = MaterialTheme.typography.button
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            onEndGameClicked?.let {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { it() }
                ) {
                    Text(
                        text = stringResource(id = R.string.button_end_game),
                        style = MaterialTheme.typography.button
                    )
                }

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
        if (uiState.loading) {
            Box(
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
                val cardTitle = if (uiState.hasDealt)
                    stringResource(id = R.string.label_post_round_scoring)
                else
                    stringResource(id = R.string.label_pre_round_bidding)

                Text(
                    text = cardTitle,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = stringResource(id = R.string.label_round_dealer, uiState.roundNumber, uiState.dealer),
                    style = MaterialTheme.typography.body2
                )
                Divider(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

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

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

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
}

@Composable
fun GameTabContent(
    uiState: UiState
) {
    val scrollState = rememberScrollState()
    val game = uiState.gameSummary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.small)
            .verticalScroll(state = scrollState),
    ) {
        if (game == null) {
            Text("Game is null... what??!!!?")
        } else {
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
            uiState = UiState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoundSummaryCardPreview() {
    WizardCompanionTheme {
        RoundSummaryCard(
            uiState = UiState(
                dealer = player1.name,
                bids = playerBids,
            ),
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

@Preview(showBackground = true)
@Composable
fun WinnerCardPreview() {
    WizardCompanionTheme {
        WinnerCard(
            game = game1,
            winner = player1,
            title = "Game Over",
            onEndGameClicked = {},
            onGoToGameDetailsClicked = {}
        )
    }
}
//endregion