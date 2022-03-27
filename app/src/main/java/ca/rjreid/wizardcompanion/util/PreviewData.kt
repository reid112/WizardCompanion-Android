package ca.rjreid.wizardcompanion.util

import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid

val player1 = Player(1, "Jim")
val player2 = Player(2, "Bobby")
val player3 = Player(3, "Catherine")

val players = listOf(player1, player2, player3)

val playerBids = listOf(
    PlayerBid(1, player1, 0, 0, 20),
    PlayerBid(2, player2, 1, 1, 30),
    PlayerBid(3, player3, 1, 0, -10)
)