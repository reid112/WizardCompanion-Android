package ca.rjreid.wizardcompanion.ui.screens.pastgames

import ca.rjreid.wizardcompanion.data.models.entities.GameDto
import ca.rjreid.wizardcompanion.domain.models.Game

data class UiState(
    var pastGames: List<Game>? = null
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
    data class OnGameClicked(val game: GameDto): UiEvent()
}