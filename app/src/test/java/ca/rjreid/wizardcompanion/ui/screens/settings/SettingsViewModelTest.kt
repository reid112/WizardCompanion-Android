package ca.rjreid.wizardcompanion.ui.screens.settings

import app.cash.turbine.test
import ca.rjreid.wizardcompanion.data.WizardRepository
import ca.rjreid.wizardcompanion.data.models.ThemeSetting
import ca.rjreid.wizardcompanion.rules.TestCoroutineRule
import ca.rjreid.wizardcompanion.util.RULES_URL
import ca.rjreid.wizardcompanion.util.Routes
import ca.rjreid.wizardcompanion.util.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {
    //region Rules
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    //endregion

    //region Mocks
    @Mock
    private lateinit var repository: WizardRepository
    //endregion

    //region Tests
    @Test
    fun `Show currently active settings - 1`() = testCoroutineRule.runBlockingTest {
        // given
        val setting = ThemeSetting.SYSTEM
        val keepScreenOn = false
        whenever(repository.getThemeSetting()).thenReturn(flowOf(setting))
        whenever(repository.getKeepScreenOnSetting()).thenReturn(flowOf(keepScreenOn))

        // when
        val viewModel = SettingsViewModel(repository)

        // then
        Assert.assertEquals(
            "Theme is not what is expected!",
            setting,
            viewModel.uiState.theme
        )
        Assert.assertFalse(
            "Keep screen on is not what is expected!",
            viewModel.uiState.keepScreenOn
        )
    }

    @Test
    fun `Show currently active settings - 2`() = testCoroutineRule.runBlockingTest {
        // given
        val setting = ThemeSetting.DARK
        val keepScreenOn = true
        whenever(repository.getThemeSetting()).thenReturn(flowOf(setting))
        whenever(repository.getKeepScreenOnSetting()).thenReturn(flowOf(keepScreenOn))

        // when
        val viewModel = SettingsViewModel(repository)

        // then
        Assert.assertEquals(
            "Theme is not what is expected!",
            setting,
            viewModel.uiState.theme
        )
        Assert.assertTrue(
            "Keep screen on is not what is expected!",
            viewModel.uiState.keepScreenOn
        )
    }

    @Test
    fun `Keep screen on setting is changed in repository when user changes it in ui`() = testCoroutineRule.runBlockingTest {
        // given
        val currentKeepScreenOnSetting = true
        val newKeepScreenOnSetting = false
        whenever(repository.getThemeSetting()).thenReturn(flowOf(ThemeSetting.DARK))
        whenever(repository.getKeepScreenOnSetting()).thenReturn(flowOf(currentKeepScreenOnSetting))

        // when
        val viewModel = SettingsViewModel(repository)
        viewModel.onEvent(UiEvent.OnChangeKeepScreenOn(newKeepScreenOnSetting))

        // then
        verify(repository, times(1)).setKeepScreenOnSetting(newKeepScreenOnSetting)
    }

    @Test
    fun `Theme setting is changed in repository when user changes it in ui`() = testCoroutineRule.runBlockingTest {
        // given
        val currentThemeSetting = ThemeSetting.DARK
        val newThemeSetting = ThemeSetting.LIGHT
        whenever(repository.getThemeSetting()).thenReturn(flowOf(currentThemeSetting))
        whenever(repository.getKeepScreenOnSetting()).thenReturn(flowOf(true))

        // when
        val viewModel = SettingsViewModel(repository)
        viewModel.onEvent(UiEvent.OnChangeTheme(newThemeSetting))

        // then
        verify(repository, times(1)).setThemeSetting(newThemeSetting)
    }

    @Test
    fun `Theme chooser dialog is shown when theme row is clicked`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getThemeSetting()).thenReturn(flowOf(ThemeSetting.SYSTEM))
        whenever(repository.getKeepScreenOnSetting()).thenReturn(flowOf(true))

        // when
        val viewModel = SettingsViewModel(repository)
        viewModel.onEvent(UiEvent.OnThemeRowClicked)

        // then
        Assert.assertTrue(
            "Theme selection dialog is not shown?!!",
            viewModel.uiState.themeSelectionDialogVisible
        )
    }

    @Test
    fun `Theme chooser dialog is shown when dialog is dismissed`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getThemeSetting()).thenReturn(flowOf(ThemeSetting.SYSTEM))
        whenever(repository.getKeepScreenOnSetting()).thenReturn(flowOf(true))

        // when
        val viewModel = SettingsViewModel(repository)
        viewModel.onEvent(UiEvent.OnDismissThemeSelection)

        // then
        Assert.assertFalse(
            "Theme selection dialog is shown?!!",
            viewModel.uiState.themeSelectionDialogVisible
        )
    }

    @Test
    fun `Navigate to game rules when rules row is clicked`() = testCoroutineRule.runBlockingTest {
        // given
        whenever(repository.getThemeSetting()).thenReturn(flowOf(ThemeSetting.SYSTEM))
        whenever(repository.getKeepScreenOnSetting()).thenReturn(flowOf(true))

        // when
        val viewModel = SettingsViewModel(repository)

        // then
        viewModel.actions.test {
            viewModel.onEvent(UiEvent.OnRulesRowClicked)

            val action = expectMostRecentItem()
            Assert.assertTrue(
                "Action was not a NavigateToExternalUrl action!",
                action is Action.NavigateToExternalUrl
            )
            Assert.assertTrue(
                "Url was not the rules url!",
                (action as Action.NavigateToExternalUrl).url == RULES_URL
            )
        }
    }
    //endregion
}