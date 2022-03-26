package ca.rjreid.wizardcompanion.util

sealed class Screen(val route: String) {
    object Home: Screen(Routes.HOME.route)
    object PastGames: Screen(Routes.PAST_GAMES.route)
    object Settings: Screen(Routes.SETTINGS.route)
    object EnterPlayers: Screen(Routes.ENTER_PLAYERS.route)
    object Score: Screen("${Routes.SCORE.route}/{${Arguments.GAME_ID.arg}}")
    object GameDetails: Screen("${Routes.GAME_DETAILS.route}/{${Arguments.GAME_ID.arg}}")
}

enum class Routes(val route: String) {
    HOME("home"),
    PAST_GAMES("pastGames"),
    SETTINGS("settings"),
    ENTER_PLAYERS("enterPlayers"),
    SCORE("score"),
    GAME_DETAILS("gameDetails")
}

enum class Arguments(val arg: String) {
    GAME_ID("gameId")
}