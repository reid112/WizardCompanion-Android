package ca.rjreid.wizardcompanion.ui.screens.home

import app.cash.turbine.test
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.rules.TestCoroutineRule
import ca.rjreid.wizardcompanion.util.Routes
import ca.rjreid.wizardcompanion.util.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    //region Rules
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    //endregion

    //region Mocks
    @Mock private lateinit var repository: WizardRepository
    //endregion

    //region Tests
    @Test
    fun `Game in progress card not shown when no game is in progress`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(null) })

        // when
        val viewModel = HomeViewModel(repository)

        // then
        assertFalse(
            "Resume game card is visible!",
            viewModel.uiState.resumeGameCardVisible
        )
    }


    @Test
    fun `Start game dialog is shown when new game is clicked with a game already in progress`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(TestData.gameWithPlayersAndRounds) })

        // when
        val viewModel = HomeViewModel(repository)
        viewModel.onEvent(UiEvent.OnNewGameClicked)

        // then
        assertTrue(
            "Resume game card is not visible!",
            viewModel.uiState.startGameDialogVisible
        )
    }


    @Test
    fun `Start game dialog is not shown when new game is clicked with no game in progress`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(null) })

        // when
        val viewModel = HomeViewModel(repository)
        viewModel.onEvent(UiEvent.OnNewGameClicked)

        // then
        assertFalse(
            "Resume game card is visible!",
            viewModel.uiState.startGameDialogVisible
        )
    }

    @Test
    fun `Navigate to enter players action is sent when new game is clicked with no game in progress`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(null) })

        // when
        val viewModel = HomeViewModel(repository)

        // then
        viewModel.actions.test {
            viewModel.onEvent(UiEvent.OnNewGameClicked)

            val action = expectMostRecentItem()
            assertTrue(
                "Action was not a navigation action!",
                action is Action.Navigate
            )
            assertTrue(
                "Navigation route was not the enter players route!",
                (action as Action.Navigate).route == Routes.ENTER_PLAYERS.route
            )
        }
    }

    @Test
    fun `No action is sent when new game is clicked with game in progress`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(TestData.gameWithPlayersAndRounds) })

        // when
        val viewModel = HomeViewModel(repository)

        // then
        viewModel.actions.test {
            viewModel.onEvent(UiEvent.OnNewGameClicked)
            expectNoEvents()
        }
    }

    @Test
    fun `Navigate to in progress game when when resume game clicked`() = testCoroutineRule.runBlockingTest {
        // given
        val game = TestData.gameWithPlayersAndRounds
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(game) })

        // when
        val viewModel = HomeViewModel(repository)

        // then
        viewModel.actions.test {
            viewModel.onEvent(UiEvent.OnResumeGameClicked)

            val action = expectMostRecentItem()
            assertTrue(
                "Action was not a navigation action!",
                action is Action.Navigate
            )
            assertTrue(
                "Navigation route was not the score route!",
                (action as Action.Navigate).route == "${Routes.SCORE.route}/${game.game.id}"
            )
        }
    }

    @Test
    fun `Start game dialog is hidden when start game dialog is cancelled`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(null) })

        // when
        val viewModel = HomeViewModel(repository)
        viewModel.onEvent(UiEvent.OnStartGameDialogCancel)

        // then
        assertFalse(
            "Start game dialog is visible!",
            viewModel.uiState.startGameDialogVisible
        )
    }

    @Test
    fun `Start game dialog is hidden when start game dialog is confirmed`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(null) })

        // when
        val viewModel = HomeViewModel(repository)
        viewModel.onEvent(UiEvent.OnStartGameDialogConfirm)

        // then
        assertFalse(
            "Start game dialog is visible!",
            viewModel.uiState.startGameDialogVisible
        )
    }

    @Test
    fun `Navigate to enter players when when start game dialog confirmed`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getInProgressGame()).thenReturn(flow { emit(null) })

        // when
        val viewModel = HomeViewModel(repository)

        // then
        viewModel.actions.test {
            viewModel.onEvent(UiEvent.OnStartGameDialogConfirm)

            val action = expectMostRecentItem()
            assertTrue(
                "Action was not a navigation action!",
                action is Action.Navigate
            )
            assertTrue(
                "Navigation route was not the enter players route!",
                (action as Action.Navigate).route == Routes.ENTER_PLAYERS.route
            )
        }
    }
    //endregion
}