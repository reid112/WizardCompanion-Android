package ca.rjreid.wizardcompanion.ui.bottomsheets.startnewgame

data class UiState(
    var players: List<String> = mutableListOf("","","")
)

sealed class UiEvent {
    data class OnPlayerUpdated(val index: Int, val player: String): UiEvent()
    object OnAddPlayerClicked: UiEvent()
}