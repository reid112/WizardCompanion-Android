package ca.rjreid.wizardcompanion.domain.models

data class Round(
    val number: Int,
    val playerBids: List<PlayerBid>,
)