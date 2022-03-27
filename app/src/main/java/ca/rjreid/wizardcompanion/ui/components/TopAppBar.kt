package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.util.Routes
import ca.rjreid.wizardcompanion.util.Screen

@Composable
fun TopAppBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var elevation by remember { mutableStateOf(AppBarDefaults.TopAppBarElevation) }
    var currentTitle by remember { mutableStateOf("") }
    var navIcon by remember { mutableStateOf<AppBarIcon?>(null)}

    when(navBackStackEntry?.destination?.route) {
        Screen.Home.route -> {
            currentTitle = stringResource(id = R.string.screen_title_home)
            elevation = AppBarDefaults.TopAppBarElevation
            navIcon = null
        }
        Screen.PastGames.route -> {
            currentTitle = stringResource(id = R.string.screen_title_past_games)
            elevation = AppBarDefaults.TopAppBarElevation
            navIcon = null
        }
        Screen.Settings.route -> {
            currentTitle = stringResource(id = R.string.screen_title_settings)
            elevation = AppBarDefaults.TopAppBarElevation
            navIcon = null
        }
        Screen.EnterPlayers.route -> {
            currentTitle = stringResource(id = R.string.screen_title_enter_players)
            elevation = AppBarDefaults.TopAppBarElevation
            navIcon = AppBarIcon.BACK
        }
        Screen.Score.route -> {
            currentTitle = stringResource(id = R.string.screen_title_score)
            elevation = 0.dp
            navIcon = AppBarIcon.BACK
        }
        Screen.GameDetails.route -> {
            currentTitle = stringResource(id = R.string.screen_title_game_details)
            elevation = AppBarDefaults.TopAppBarElevation
            navIcon = AppBarIcon.BACK
        }
        else -> {
            currentTitle = stringResource(id = R.string.app_name)
            elevation = AppBarDefaults.TopAppBarElevation
            navIcon = null
        }
    }

    TopAppBar(
        title = { Text(text = currentTitle) },
        navigationIcon = navIcon?.let { { NavIcon(navController, it) } },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = elevation
    )
}

@Composable
fun NavIcon(
    navController: NavController,
    navIcon: AppBarIcon
) {
    when (navIcon) {
        AppBarIcon.BACK -> {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.content_description_back)
                )
            }
        }
    }
}

enum class AppBarIcon {
    BACK
}