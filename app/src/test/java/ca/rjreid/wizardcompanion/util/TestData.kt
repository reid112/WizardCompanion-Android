package ca.rjreid.wizardcompanion.util

import ca.rjreid.wizardcompanion.data.models.entities.GameDto
import ca.rjreid.wizardcompanion.data.models.entities.PlayerBidDto
import ca.rjreid.wizardcompanion.data.models.entities.PlayerDto
import ca.rjreid.wizardcompanion.data.models.entities.RoundDto
import ca.rjreid.wizardcompanion.data.models.entities.relations.GameWithPlayersAndRounds
import ca.rjreid.wizardcompanion.data.models.entities.relations.PlayerBidWithPlayer
import ca.rjreid.wizardcompanion.data.models.entities.relations.RoundWithDealer
import java.util.*

class TestData {
    companion object {
        //region Players
        val player1 = PlayerDto(1, "Jimmy")
        val player2 = PlayerDto(2, "Bart")
        val player3 = PlayerDto(3, "Jill")
        //endregion

        //region Game
        val game = GameDto(1, Date(), 2)
        //endregion

        //region Round 1
        val round1 = RoundDto(1, game.id, 1, player1.id)
        val player1Round1Bid = PlayerBidWithPlayer(
            PlayerBidDto(1, 1, player1.id, 0, 0, 0),
            player1
        )
        val player2Round1Bid = PlayerBidWithPlayer(
            PlayerBidDto(2, 1, player2.id, 1, 1, 30),
            player2
        )
        val player3Round1Bid = PlayerBidWithPlayer(
            PlayerBidDto(3, 1, player3.id, 1, 0, -10),
            player3
        )
        val round1WithDealer = RoundWithDealer(round1, player1, listOf(
            player1Round1Bid, player2Round1Bid, player3Round1Bid
        ))
        //endregion

        //region Round2
        val round2 = RoundDto(2, game.id, 2, player2.id)
        val player1Round2Bid = PlayerBidWithPlayer(
            PlayerBidDto(4, 2, player1.id, 1, 1, 30),
            player1
        )
        val player2Round2Bid = PlayerBidWithPlayer(
            PlayerBidDto(5, 2, player2.id, 1, 1, 60),
            player2
        )
        val player3Round2Bid = PlayerBidWithPlayer(
            PlayerBidDto(6, 2, player3.id, 1, 0, -20),
            player3
        )
        val round2WithDealer = RoundWithDealer(round1, player1, listOf(
            player1Round2Bid, player2Round2Bid, player3Round2Bid
        ))
        //endregion

        //region GameWithDetails
        val gameWithPlayersAndRounds = GameWithPlayersAndRounds(
            game = game,
            players = listOf(player1, player2, player3),
            rounds = listOf(round1WithDealer, round2WithDealer),
            winner = player2
        )
        //endregion
    }
}