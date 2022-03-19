package ca.rjreid.wizardcompanion.ui.screens.score

import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import java.util.*

data class UiState(
    var forceUpdate: String = UUID.randomUUID().toString(),
    var hasDealt: Boolean = false,
    var isLastRound: Boolean = false,
    var roundNumber: Int = 1,
    var dealer: String = "",
    var bids: List<PlayerBid> = listOf(),
    var nextRoundButtonEnabled: Boolean = false,
    var winner: Player? = null,
    var gameSummary: Game? = null,
    var leaveDialogVisible: Boolean = false,
)

sealed class Action {
    object PopBackStack: Action()
}

sealed class UiEvent {
    data class OnAddBidClicked(val bid: PlayerBid): UiEvent()
    data class OnAddActualClicked(val bid: PlayerBid): UiEvent()
    data class OnRemoveBidClicked(val bid: PlayerBid): UiEvent()
    data class OnRemoveActualClicked(val bid: PlayerBid): UiEvent()
    object OnDealClicked: UiEvent()
    object OnNextRoundClicked: UiEvent()
    object OnFinishGameClicked: UiEvent()
    object OnEndGameClicked: UiEvent()
    object OnBackPressed: UiEvent()
    object OnLeaveDialogCancel: UiEvent()
    object OnLeaveDialogConfirm: UiEvent()
}