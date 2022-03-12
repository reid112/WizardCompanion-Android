package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.util.Screen

@Composable
fun TopAppBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentTitle = when(navBackStackEntry?.destination?.route) {
        Screen.Home.route -> stringResource(id = R.string.screen_title_home)
        Screen.PastGames.route -> stringResource(id = R.string.screen_title_past_games)
        Screen.Settings.route -> stringResource(id = R.string.screen_title_settings)
        Screen.EnterPlayers.route -> stringResource(id = R.string.screen_title_enter_players)
        Screen.Score.route -> stringResource(id = R.string.screen_title_score)
        Screen.GameDetails.route -> stringResource(id = R.string.screen_title_game_details)
        else -> ""
    }

    TopAppBar(
        title = { Text(text = currentTitle) },
        navigationIcon = null,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp
    )
}