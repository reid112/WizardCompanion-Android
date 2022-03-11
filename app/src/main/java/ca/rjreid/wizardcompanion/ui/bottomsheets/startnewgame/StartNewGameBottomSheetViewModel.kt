package ca.rjreid.wizardcompanion.ui.bottomsheets.startnewgame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StartNewGameBottomSheetViewModel: ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set
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
        }
    }
    //endregion

    //region Helpers
    private fun updatePlayer(index: Int, player: String) {
        val players = uiState.players.toMutableList()
        players[index] = player
        uiState = uiState.copy(players = players)
    }

    private fun addEmptyPlayer() {
        val players = uiState.players.toMutableList()
        players.add("")
        uiState = uiState.copy(players = players)
    }
    //endregion
}