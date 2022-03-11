package ca.rjreid.wizardcompanion.util

sealed class Screen(val route: String) {
    object Home: Screen("home_screen")
    object PastGames: Screen("past_games_screen")
    object Settings: Screen("settings_screen")
    object EnterPlayers: Screen("enter_players_screen")
    object Score: Screen("score_screen")
    object GameDetails: Screen("game_details_screen")
}