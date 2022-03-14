package ca.rjreid.wizardcompanion.ui.screens.score

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ca.rjreid.wizardcompanion.data.ScoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreManager: ScoreManager
): ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState(scoreManager.getGame().rounds.last()))
        private set
    //endregion

    //region Public
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnAddBidClicked -> {
                scoreManager.addOneToBid(event.bid)
                uiState = uiState.copy(currentRound = scoreManager.getGame().rounds.last())
            }
            is UiEvent.OnRemoveBidClicked -> {
                scoreManager.subtractOneToBid(event.bid)
                uiState = uiState.copy(currentRound = scoreManager.getGame().rounds.last())
            }
        }
    }
    //endregion

    //region Helpers

    //endregion
}