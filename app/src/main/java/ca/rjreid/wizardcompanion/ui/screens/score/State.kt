package ca.rjreid.wizardcompanion.ui.screens.score

import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import java.util.*

data class UiState(
    var forceUpdate: String = UUID.randomUUID().toString(),
    var hasDealt: Boolean = false,
    var roundNumber: Int = 1,
    var dealer: String = "",
    var bids: List<PlayerBid> = listOf(),
    var nextRoundButtonEnabled: Boolean = false
)

sealed class UiEvent {
    data class OnAddBidClicked(val bid: PlayerBid): UiEvent()
    data class OnAddActualClicked(val bid: PlayerBid): UiEvent()
    data class OnRemoveBidClicked(val bid: PlayerBid): UiEvent()
    data class OnRemoveActualClicked(val bid: PlayerBid): UiEvent()
    object OnDealClicked: UiEvent()
    object OnNextRoundClicked: UiEvent()
}