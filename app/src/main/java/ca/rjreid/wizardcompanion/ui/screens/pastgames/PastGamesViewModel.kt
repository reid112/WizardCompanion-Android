package ca.rjreid.wizardcompanion.ui.screens.pastgames

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.domain.mappers.toGame
import ca.rjreid.wizardcompanion.util.Routes
import ca.rjreid.wizardcompanion.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PastGamesViewModel @Inject constructor(
    private val repository: WizardRepository
): ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow()
    //endregion

    //region Init
    init {
        loadPastGames()
    }
    //endregion

    //region Public
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnGameClicked -> {
                sendAction(Action.Navigate("${Routes.GAME_DETAILS.route}/${event.gameId}"))
            }
        }
    }
    //endregion

    //region Helpers
    private fun loadPastGames() {
        viewModelScope.launch {
            repository.getPastGamesWithDetails().collect { gamesWithPlayersAndRounds ->
                val noPastGames = gamesWithPlayersAndRounds.isEmpty()
                val pastGames = if (noPastGames) {
                    null
                } else {
                    gamesWithPlayersAndRounds
                        .map { it.toGame() }
                        .sortedByDescending { it.date }
                }

                uiState = uiState.copy(
                    noPastGames = noPastGames,
                    pastGames = pastGames
                )
            }
        }
    }

    private fun sendAction(action: Action) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }
    //endregion
}