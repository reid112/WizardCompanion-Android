package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.util.Screen

@Composable
fun TopAppBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var elevation by remember { mutableStateOf(AppBarDefaults.TopAppBarElevation) }
    var currentTitle by remember { mutableStateOf("") }

    when(navBackStackEntry?.destination?.route) {
        Screen.Home.route -> currentTitle = stringResource(id = R.string.screen_title_home)
        Screen.PastGames.route -> currentTitle = stringResource(id = R.string.screen_title_past_games)
        Screen.Settings.route -> currentTitle = stringResource(id = R.string.screen_title_settings)
        Screen.EnterPlayers.route -> currentTitle = stringResource(id = R.string.screen_title_enter_players)
        Screen.Score.route -> {
            currentTitle = stringResource(id = R.string.screen_title_score)
            elevation = 0.dp
        }
        Screen.GameDetails.route -> currentTitle = stringResource(id = R.string.screen_title_game_details)
    }

    TopAppBar(
        title = { Text(text = currentTitle) },
        navigationIcon = null,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = elevation
    )
}