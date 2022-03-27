package ca.rjreid.wizardcompanion.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.data.models.ThemeSetting
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    SettingsCard(uiState, viewModel::toggleScreen)
}

@Composable
fun SettingsCard(
    state: UiState,
    onThemeRowClicked: () -> Unit = {},
    onChangeTheme: (ThemeSetting) -> Unit = {},
    onToggleScreen: () -> Unit = {}
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.extraLarge)
                    .clickable { onThemeRowClicked() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(R.string.setting_theme))
                Text(
                    text = stringResource(state.theme.stringResId),
                    style = MaterialTheme.typography.caption
                )
            }
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth().height(MaterialTheme.spacing.extraLarge),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.setting_keep_screen_on))
                Switch(
                    checked = state.keepScreenOn,
                    onCheckedChange = { onToggleScreen() }
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsCardPreview() {
    WizardCompanionTheme {
        SettingsCard(state = UiState())
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingsCardDarkPreview() {
    WizardCompanionTheme {
        SettingsCard(state = UiState())
    }
}