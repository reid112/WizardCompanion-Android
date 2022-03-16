package ca.rjreid.wizardcompanion.data.models.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import ca.rjreid.wizardcompanion.data.models.entities.PlayerBidDto
import ca.rjreid.wizardcompanion.data.models.entities.PlayerDto
import ca.rjreid.wizardcompanion.data.models.entities.RoundDto

data class RoundWithDealer(
    @Embedded
    var round: RoundDto,

    @Relation(
        parentColumn = "dealer_id",
        entityColumn = "id"
    )
    var dealer: PlayerDto,

    @Relation(
        parentColumn = "id",
        entityColumn = "round_id",
        entity = PlayerBidDto::class
    )
    var bids: List<PlayerBidWithPlayer>
)