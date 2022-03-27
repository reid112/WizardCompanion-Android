package ca.rjreid.wizardcompanion.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.data.models.ThemeSetting
import ca.rjreid.wizardcompanion.ui.components.SingleSelectDialog
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import kotlinx.coroutines.flow.collect

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { action ->
            when(action) {
                is Action.NavigateToExternalUrl -> {
                    uriHandler.openUri(action.url)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SettingsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            state = uiState,
            onThemeRowClicked = { viewModel.onEvent(UiEvent.OnThemeRowClicked) },
            onRulesRowClicked = { viewModel.onEvent(UiEvent.OnRulesRowClicked) },
            onChangeTheme = { viewModel.onEvent(UiEvent.OnChangeTheme(it)) },
            onToggleScreen = { viewModel.onEvent(UiEvent.OnChangeKeepScreenOn(it)) },
            onDismissThemeSelection = { viewModel.onEvent(UiEvent.OnDismissThemeSelection)}
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp),
            text = stringResource(id = R.string.label_version_number, uiState.versionNumber),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    state: UiState,
    onThemeRowClicked: () -> Unit = {},
    onRulesRowClicked: () -> Unit = {},
    onChangeTheme: (ThemeSetting) -> Unit = {},
    onToggleScreen: (Boolean) -> Unit = {},
    onDismissThemeSelection: () -> Unit = {}
) {
    if (state.themeSelectionDialogVisible) {
        SingleSelectDialog(title = stringResource(R.string.dialog_choose_theme_title),
            optionsList = ThemeSetting.values().map { stringResource(it.stringResId) },
            defaultSelected = state.theme.value,
            onOptionSelected = {
                onChangeTheme(ThemeSetting.fromInt(it) ?: ThemeSetting.SYSTEM)
            },
            onDismissRequest = { onDismissThemeSelection() })
    }

    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.extraLarge)
                    .clickable { onToggleScreen(!state.keepScreenOn) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.setting_keep_screen_on))
                Switch(
                    checked = state.keepScreenOn,
                    onCheckedChange = { onToggleScreen(it) }
                )
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.extraLarge)
                    .clickable { onRulesRowClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.label_rules))
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