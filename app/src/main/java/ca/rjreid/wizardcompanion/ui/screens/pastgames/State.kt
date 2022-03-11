package ca.rjreid.wizardcompanion.ui.screens.pastgames

import ca.rjreid.wizardcompanion.data.entities.Game
import ca.rjreid.wizardcompanion.data.entities.Round

data class UiState(
    var pastGames: Map<Game, List<Round>> = emptyMap()
)

sealed class Action {
    object PopBackStack: Action()
    data class Navigate(val route: String): Action()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): Action()
}

sealed class UiEvent {
    data class OnGameClicked(val game: Game): UiEvent()
}