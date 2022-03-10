package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ca.rjreid.wizardcompanion.ui.screens.gamedetails.GameDetailsScreen
import ca.rjreid.wizardcompanion.ui.screens.home.HomeScreen
import ca.rjreid.wizardcompanion.ui.screens.pastgames.PastGamesScreen
import ca.rjreid.wizardcompanion.ui.screens.score.ScoreScreen
import ca.rjreid.wizardcompanion.ui.screens.settings.SettingsScreen
import ca.rjreid.wizardcompanion.util.Screen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@Composable
fun NavigationGraph(
    navController: NavHostController,
    padding: PaddingValues
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        builder = {
            composable(route = Screen.Home.route) {
                HomeScreen(onNavigate = { navController.navigate(it) })
            }
            composable(route = Screen.PastGames.route) {
                PastGamesScreen()
            }
            composable(route = Screen.Settings.route) {
                SettingsScreen()
            }
            composable(
                route = Screen.Score.route,
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    )
                },
                popExitTransition = {
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    )
                }
            ) {
                ScoreScreen()
            }
            composable(
                route = Screen.GameDetails.route,
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    )
                },
                popExitTransition = {
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    )
                }
            ) {
                GameDetailsScreen()
            }
        })
}