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
fun ResumeGameCard(
    modifier: Modifier = Modifier,
    onResumeGameClick: () -> Unit
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = stringResource(id = R.string.label_resume_game),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_game_in_progress),
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onResumeGameClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.button_resume_game),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResumeGameCardPreview() {
    WizardCompanionTheme {
        ResumeGameCard(onResumeGameClick = {})
    }
}
