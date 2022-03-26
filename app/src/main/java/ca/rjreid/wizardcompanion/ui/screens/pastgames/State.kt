package ca.rjreid.wizardcompanion.ui.screens.pastgames

import ca.rjreid.wizardcompanion.domain.models.Game

data class UiState(
    var noPastGames: Boolean = true,
    var pastGames: List<Game>? = null
)

sealed class Action {
    data class Navigate(val route: String): Action()
}

sealed class UiEvent {
    data class OnGameClicked(val gameId: Long): UiEvent()
}