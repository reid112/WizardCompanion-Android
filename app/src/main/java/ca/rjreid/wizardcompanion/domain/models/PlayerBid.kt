package ca.rjreid.wizardcompanion.domain.models

data class PlayerBid(
    val player: Player,
    val bid: Int,
    val actual: Int? = null,
    val score: Int? = null
)