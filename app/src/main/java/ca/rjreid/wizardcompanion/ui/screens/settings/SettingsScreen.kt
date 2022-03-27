package ca.rjreid.wizardcompanion.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(text = uiState.theme.name)
        Text(text = uiState.keepScreenOn.toString())

        Button(onClick = { viewModel.toggleTheme() }) {
            Text(text = "Toggle theme")
        }

        Button(onClick = { viewModel.toggleScreen() }) {
            Text(text = "Toggle screen")
        }
    }
}