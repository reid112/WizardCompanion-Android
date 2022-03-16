package ca.rjreid.wizardcompanion.domain.mappers

import ca.rjreid.wizardcompanion.data.models.entities.GameDto
import ca.rjreid.wizardcompanion.data.models.entities.PlayerBidDto
import ca.rjreid.wizardcompanion.data.models.entities.PlayerDto
import ca.rjreid.wizardcompanion.data.models.entities.RoundDto
import ca.rjreid.wizardcompanion.data.models.entities.relations.GameWithPlayersAndRounds
import ca.rjreid.wizardcompanion.data.models.entities.relations.PlayerBidWithPlayer
import ca.rjreid.wizardcompanion.data.models.entities.relations.RoundWithDealer
import ca.rjreid.wizardcompanion.domain.models.Game
import ca.rjreid.wizardcompanion.domain.models.Player
import ca.rjreid.wizardcompanion.domain.models.PlayerBid
import ca.rjreid.wizardcompanion.domain.models.Round

fun Game.toGameDto() = GameDto(
    id = id,
    date = date,
    winnerId = winner?.id
)

fun GameWithPlayersAndRounds.toGame() = Game(
    id = game.id,
    date = game.date,
    players = players.map { it.toPlayer() },
    rounds = rounds.map { it.toRound() },
    winner = winner?.toPlayer()
)

fun Player.toPlayerDto() = PlayerDto(
    id = id,
    name = name
)

fun PlayerDto.toPlayer() = Player(
    id = id,
    name = name
)

fun Round.toRoundDto(gameId: Long) = RoundDto(
    id = id,
    gameId = gameId,
    roundNumber = number,
    dealerId = dealer.id
)

fun RoundWithDealer.toRound() = Round(
    id = round.id,
    number = round.roundNumber,
    dealer = dealer.toPlayer(),
    playerBids = bids.map { it.toPlayerBid() }
)

fun PlayerBidWithPlayer.toPlayerBid() = PlayerBid(
    id = playerBid.id,
    player = player.toPlayer(),
    bid = playerBid.bid,
    actual = playerBid.actual,
    score = playerBid.score
)

fun PlayerBid.toPlayerBidDto(roundId: Long) = PlayerBidDto(
    id = id,
    roundId = roundId,
    playerId = player.id,
    bid = bid,
    actual = actual,
    score = score
)