package ca.rjreid.wizardcompanion.ui.screens.score

import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.domain.models.Round

data class UiState(
    var currentRound: Round
)

sealed class UiEvent {
    data class OnAddBidClicked(val bid: PlayerBid): UiEvent()
    data class OnRemoveBidClicked(val bid: PlayerBid): UiEvent()
}