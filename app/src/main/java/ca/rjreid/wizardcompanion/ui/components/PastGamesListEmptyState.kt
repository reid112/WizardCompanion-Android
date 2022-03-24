package ca.rjreid.wizardcompanion.ui.components.pastgameslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
fun PastGamesEmptyState(modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_past_games_empty_state),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PastGamesEmptyStatePreview() {
    WizardCompanionTheme {
        PastGamesEmptyState()
    }
}