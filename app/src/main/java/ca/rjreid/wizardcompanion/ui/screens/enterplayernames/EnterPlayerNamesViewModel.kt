package ca.rjreid.wizardcompanion.ui.screens.enterplayernames

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.ScoreManager
import ca.rjreid.wizardcompanion.util.MAX_PLAYER_COUNT
import ca.rjreid.wizardcompanion.util.MIN_PLAYER_COUNT
import ca.rjreid.wizardcompanion.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterPlayerNamesViewModel @Inject constructor(
    private val scoreManager: ScoreManager
) : ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow()
    //endregion

    //region Public
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnPlayerUpdated -> {
                updatePlayer(event.index, event.player)
            }
            is UiEvent.OnAddPlayerClicked -> {
                addEmptyPlayer()
            }
            is UiEvent.OnStartGameClick -> {
                startGame()
            }
        }
    }
    //endregion

    //region Helpers
    private fun updatePlayer(index: Int, player: String) {
        val players = uiState.players.toMutableList()
        players[index] = player
        uiState = uiState.copy(players = players)

        setStartGameButtonState()
    }

    private fun setStartGameButtonState() {
        val isButtonEnabled = uiState.players.filter { it.isNotBlank() }.count() >= MIN_PLAYER_COUNT
        uiState = uiState.copy(startGameButtonEnabled = isButtonEnabled)
    }

    private fun addEmptyPlayer() {
        if (uiState.players.count() >= MAX_PLAYER_COUNT) return

        val players = uiState.players.toMutableList()
        players.add("")
        uiState = uiState.copy(players = players)
    }

    private fun startGame() {
        viewModelScope.launch {
            scoreManager.startNewGame(uiState.players)
        }

        sendAction(Action.PopBackStack)
        sendAction(Action.Navigate(Screen.Score.route))
    }

    private fun sendAction(action: Action) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }
    //endregion
}