package ca.rjreid.wizardcompanion.ui.screens.pastgames

import ca.rjreid.wizardcompanion.data.models.entities.GameDto
import ca.rjreid.wizardcompanion.data.models.entities.RoundDto

data class UiState(
    var pastGames: Map<GameDto, List<RoundDto>> = emptyMap()
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