package ca.rjreid.wizardcompanion.domain.models

import java.util.*

data class Game(
    val date: Date,
    val players: List<Player>,
    val rounds: List<Round>
)