package ca.rjreid.wizardcompanion.ui.screens.enterplayernames

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.domain.ScoreManager
import ca.rjreid.wizardcompanion.util.MAX_PLAYER_COUNT
import ca.rjreid.wizardcompanion.util.MIN_PLAYER_COUNT
import ca.rjreid.wizardcompanion.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EnterPlayerNamesViewModel @Inject constructor(
    private val repository: WizardRepository,
    private val scoreManager: ScoreManager
) : ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow()
    //endregion

    //region Init
    init {
        viewModelScope.launch {
            repository.removeAllInProgressGames()
        }
    }
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
                val players = uiState.players.filter { it.isNotBlank() }
                uiState = uiState.copy(players = players, chooseDealerDialogVisible = true)
            }
            is UiEvent.OnDealerSelected -> {
                reorderPlayers(event.index)
                uiState = uiState.copy(chooseDealerDialogVisible = false)
                startGame()
            }
            is UiEvent.OnDismissDealerDialog -> {
                uiState = uiState.copy(chooseDealerDialogVisible = false)
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

    private fun reorderPlayers(dealerIndex: Int) {
        val players = uiState.players
        val rotate = players.size - dealerIndex

        Collections.rotate(players, rotate)

        uiState = uiState.copy(players = players)
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
            sendAction(Action.PopBackStack)
            sendAction(Action.Navigate("${Routes.SCORE.route}/0"))
        }
    }

    private fun sendAction(action: Action) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }
    //endregion
}