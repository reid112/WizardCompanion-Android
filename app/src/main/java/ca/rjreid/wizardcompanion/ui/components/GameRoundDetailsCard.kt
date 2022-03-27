package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.domain.models.Round
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing
import ca.rjreid.wizardcompanion.util.player1
import ca.rjreid.wizardcompanion.util.playerBids

@Composable
fun GameRoundDetailsCard(
    modifier: Modifier = Modifier,
    round: Round
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
            Text(
                text = stringResource(id = R.string.label_round, round.number),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_dealer, round.dealer.name),
                style = MaterialTheme.typography.body2
            )
            Divider(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.label_player),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(4f)
                )
                Text(
                    text = stringResource(id = R.string.label_score),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = stringResource(id = R.string.label_bid),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = stringResource(id = R.string.label_actual),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
            }
            
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

            round.playerBids.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.player.name,
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.weight(4f)
                    )
                    Text(
                        text = it.score.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = it.bid.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = it.actual.toString(),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameRoundDetailsCardPreview() {
    WizardCompanionTheme {
        GameRoundDetailsCard(
            round = Round(
                id = 0,
                number = 3,
                dealer = player1,
                playerBids = playerBids
            )
        )
    }
}