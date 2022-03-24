package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing

@Composable
fun PlayNowCard(
    modifier: Modifier = Modifier,
    onPlayNowClick: () -> Unit
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_play_now),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_start_game),
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onPlayNowClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.button_new_game),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayNowCardPreview() {
    WizardCompanionTheme {
        PlayNowCard(onPlayNowClick = {})
    }
}