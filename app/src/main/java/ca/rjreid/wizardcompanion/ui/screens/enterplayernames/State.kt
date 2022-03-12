package ca.rjreid.wizardcompanion.ui.screens.enterplayernames

import ca.rjreid.wizardcompanion.util.MIN_PLAYER_COUNT

data class UiState(
    var players: List<String> = MutableList(MIN_PLAYER_COUNT) { "" },
    var startGameButtonEnabled: Boolean = false
)

sealed class Action {
    object PopBackStack: Action()
    data class Navigate(val route: String): Action()
}

sealed class UiEvent {
    data class OnPlayerUpdated(val index: Int, val player: String): UiEvent()
    object OnAddPlayerClicked: UiEvent()
    object OnStartGameClick: UiEvent()
}