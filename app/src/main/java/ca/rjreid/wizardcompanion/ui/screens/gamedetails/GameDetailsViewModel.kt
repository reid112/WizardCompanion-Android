package ca.rjreid.wizardcompanion.ui.screens.gamedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.domain.mappers.toGame
import ca.rjreid.wizardcompanion.util.Arguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val repository: WizardRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set
    //endregion

    //region Init
    init {
        savedStateHandle.get<Long>(Arguments.GAME_ID.arg)?.let {
            loadGameDetails(it)
        } ?: processError()
    }
    //endregion

    //region Helpers
    private fun loadGameDetails(gameId: Long) {
        viewModelScope.launch {
            repository.getGameWithDetails(gameId).collect { gameWithPlayersAndRounds ->
                gameWithPlayersAndRounds?.let {
                    val game = it.toGame()
                    game.rounds = game.rounds.sortedByDescending { round -> round.number }

                    uiState = uiState.copy(
                        loading = false,
                        game = game
                    )
                } ?: processError()
            }
        }
    }

    private fun processError() {
        uiState = uiState.copy(loading = false, error = true)
    }
    //endregion
}