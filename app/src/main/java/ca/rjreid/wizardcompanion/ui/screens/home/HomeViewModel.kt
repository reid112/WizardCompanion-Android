package ca.rjreid.wizardcompanion.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WizardRepository
): ViewModel() {
    //region Variables
    var uiState by mutableStateOf(UiState())
        private set

    private val _actions = Channel<Action>()
    val actions = _actions.receiveAsFlow()
    //endregion

    //region Public
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnNewGameClicked -> {
                sendAction(Action.Navigate(Screen.EnterPlayers.route))
            }
            is UiEvent.OnResumeGameClicked -> {
                sendAction(Action.Navigate(Screen.Score.route))
            }
        }
    }
    //endregion

    //region Helpers
    private fun sendAction(action: Action) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }
    //endregion
}