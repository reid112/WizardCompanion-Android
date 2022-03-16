package ca.rjreid.wizardcompanion.domain.models

data class Round(
    val id: Long,
    val number: Int,
    val dealer: Player,
    val playerBids: List<PlayerBid>,
)