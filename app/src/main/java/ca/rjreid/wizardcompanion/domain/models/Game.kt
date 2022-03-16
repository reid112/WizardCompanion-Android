package ca.rjreid.wizardcompanion.domain.models

import java.util.*

data class Game(
    val id: Long,
    val date: Date,
    val players: List<Player>,
    var rounds: List<Round>,
    var winner: Player? = null
)