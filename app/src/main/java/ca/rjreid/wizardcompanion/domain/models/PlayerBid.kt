package ca.rjreid.wizardcompanion.domain.models

data class PlayerBid(
    val player: Player,
    val bid: Int = 0,
    val actual: Int? = null,
    val score: Int? = null
)