package ca.rjreid.wizardcompanion.ui.screens.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
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
                0 -> RoundTabContent()
                1 -> GameTabContent()
            }
        }
    }
}

@Composable
fun RoundTabContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = MaterialTheme.spacing.medium)
            .verticalScroll(state = scrollState),
    ) {
        Text(text = "Round Tab Content")
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
fun RoundTabContentPreview() {
    WizardCompanionTheme {
        RoundTabContent()
    }
}

@Preview(showBackground = true)
@Composable
fun GameTabContentPreview() {
    WizardCompanionTheme {
        GameTabContent()
    }
}
//endregion