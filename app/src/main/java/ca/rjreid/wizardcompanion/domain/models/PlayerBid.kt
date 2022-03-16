package ca.rjreid.wizardcompanion.domain.models

data class PlayerBid(
    val id: Long,
    val player: Player,
    var bid: Int = 0,
    var actual: Int = 0,
    var score: Int = 0
)