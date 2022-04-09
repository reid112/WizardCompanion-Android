package ca.rjreid.wizardcompanion.ui.screens.pastgames

import app.cash.turbine.test
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.rules.TestCoroutineRule
import ca.rjreid.wizardcompanion.util.Routes
import ca.rjreid.wizardcompanion.util.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PastGamesViewModelTest {
    //region Rules
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    //endregion

    //region Mocks
    @Mock
    private lateinit var repository: WizardRepository
    //endregion

    //region Tests
    @Test
    fun `Show no past games when none exist`() = testCoroutineRule.runBlockingTest {
        // given

        // when
        val viewModel = PastGamesViewModel(repository)

        // then
        Assert.assertNull(
            "Past games exist somehow?!",
            viewModel.uiState.pastGames
        )
        Assert.assertTrue(
            "No past games is false, how??",
            viewModel.uiState.noPastGames
        )
    }

    @Test
    fun `Show list of past games when past games exist`() = testCoroutineRule.runBlockingTest {
        // given
        val games = listOf(TestData.gameWithPlayersAndRounds)
        whenever(repository.getPastGamesWithDetails()).thenReturn(flow { emit(games) })

        // when
        val viewModel = PastGamesViewModel(repository)

        // then
        Assert.assertEquals(
            "Past games size does not match what is expected!",
            games.size,
            viewModel.uiState.pastGames?.size
        )
        Assert.assertFalse(
            "No past games is true, how??",
            viewModel.uiState.noPastGames
        )
    }

    @Test
    fun `Navigate to game details when past game is clicked`() = testCoroutineRule.runBlockingTest {
        // given
        val games = listOf(TestData.gameWithPlayersAndRounds)
        val gameId = games[0].game.id
        whenever(repository.getPastGamesWithDetails()).thenReturn(flow { emit(games) })

        // when
        val viewModel = PastGamesViewModel(repository)

        // then
        viewModel.actions.test {
            viewModel.onEvent(UiEvent.OnGameClicked(gameId))

            val action = expectMostRecentItem()
            Assert.assertTrue(
                "Action was not a navigation action!",
                action is Action.Navigate
            )
            Assert.assertTrue(
                "Navigation route was not the game details route!",
                (action as Action.Navigate).route == "${Routes.GAME_DETAILS.route}/$gameId"
            )
        }
    }
    //endregion
}