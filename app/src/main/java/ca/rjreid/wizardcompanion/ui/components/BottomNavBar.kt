package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.util.Routes
import ca.rjreid.wizardcompanion.util.Screen


sealed class BottomNavItem(var title: Int, var icon: ImageVector, var route: String){
    object Home : BottomNavItem(R.string.bottom_bar_home, Icons.Default.Home, Routes.HOME.route)
    object PastGames: BottomNavItem(R.string.bottom_bar_past_games, Icons.Default.DateRange, Routes.PAST_GAMES.route)
    object Settings: BottomNavItem(R.string.bottom_bar_settings, Icons.Default.Settings, Routes.SETTINGS.route)
}

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.PastGames,
        BottomNavItem.Settings
    )

    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    bottomBarState = when (currentRoute) {
        Routes.HOME.route,
        Routes.PAST_GAMES.route,
        Routes.SETTINGS.route -> true
        else -> false
    }

    if (bottomBarState) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.title)
                        )
                    },
                    label = { Text(text = stringResource(id = item.title)) },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onSurface,
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}