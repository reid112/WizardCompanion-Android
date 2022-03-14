package ca.rjreid.wizardcompanion.ui.screens.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
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
import kotlinx.coroutines.launch

//region Composables
@ExperimentalPagerApi
@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val tabs = listOf(
        stringResource(id = R.string.tab_title_round),
        stringResource(id = R.string.tab_title_game)
    )

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
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
                    currentRound = uiState.currentRound,
                    onAddBidClicked = { viewModel.onEvent(UiEvent.OnAddBidClicked(it)) },
                    onRemoveBidClicked = { viewModel.onEvent(UiEvent.OnRemoveBidClicked(it)) }
                )
                1 -> GameTabContent()
            }
        }
    }
}

@Composable
fun RoundTabContent(
    currentRound: Round,
    onAddBidClicked: (PlayerBid) -> Unit,
    onRemoveBidClicked: (PlayerBid) -> Unit
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
        RoundSummaryCard(
            currentRound = currentRound,
            onAddBidClicked = onAddBidClicked,
            onRemoveBidClicked = onRemoveBidClicked
        )
    }
}

@Composable
fun RoundSummaryCard(
    modifier: Modifier = Modifier,
    currentRound: Round,
    onAddBidClicked: (PlayerBid) -> Unit,
    onRemoveBidClicked: (PlayerBid) -> Unit
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_round, currentRound.number),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_dealer, currentRound.dealer.name),
                style = MaterialTheme.typography.body2
            )
            Divider(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))

            currentRound.playerBids.forEach { playerBid->
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
                        SeparatorDot(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small))
                        Text(
                            text = playerBid.bid.toString(),
                            style = MaterialTheme.typography.subtitle2,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { onRemoveBidClicked(playerBid) }) {
                            Icon(
                                imageVector = Icons.Filled.Remove,
                                contentDescription = stringResource(id = R.string.content_description_subtract),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                        Text(
                            text = playerBid.bid.toString(),
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
                        )
                        IconButton(onClick = { onAddBidClicked(playerBid) }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(id = R.string.content_description_add),
                                tint = MaterialTheme.colors.primary
                            )
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {  }
            ) {
                Text(
                    text = stringResource(id = R.string.button_deal),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun GameTabContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.medium)
            .verticalScroll(state = scrollState),
    ) {
        Text(text = "Game Tab Content")
    }
}
//endregion

//region Previews
@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WizardCompanionTheme {
        ScoreScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GameTabContentPreview() {
    WizardCompanionTheme {
        GameTabContent()
    }
}

@Preview(showBackground = true)
@Composable
fun RoundSummaryCardPreview() {
    val round = Round(3, Player("Dave"), listOf(
        PlayerBid(
            Player("Riley"),
            0,
            0,
            888
        ),
        PlayerBid(
            Player("Britt"),
            2,
            2,
            80
        ),
        PlayerBid(
            Player("Dave"),
            1,
            1,
            -10
        )
    ))

    WizardCompanionTheme {
        RoundSummaryCard(
            currentRound = round,
            onAddBidClicked = { },
            onRemoveBidClicked = { }
        )
    }
}
//endregion