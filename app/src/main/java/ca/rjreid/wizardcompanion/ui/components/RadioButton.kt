package ca.rjreid.wizardcompanion.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing

@Composable
fun RadioButton(text: String, selectedValue: String, onClickListener: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = (text == selectedValue),
                onClick = {
                    onClickListener(text)
                }
            )
            .padding(horizontal = MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material.RadioButton(
            selected = (text == selectedValue),
            onClick = {
                onClickListener(text)
            }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = MaterialTheme.spacing.small)
        )
    }
}

@Preview
@Composable
fun RadioButtonPreview() {
    WizardCompanionTheme {
        RadioButton(text = "Text", selectedValue = "Text", onClickListener = {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RadioButtonDarkPreview() {
    WizardCompanionTheme {
        RadioButton(text = "Text", selectedValue = "Text", onClickListener = {})
    }
}

@Preview
@Composable
fun RadioButton2Preview() {
    WizardCompanionTheme {
        RadioButton(text = "Text", selectedValue = "Text2", onClickListener = {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RadioButton2DarkPreview() {
    WizardCompanionTheme {
        RadioButton(text = "Text", selectedValue = "Text2", onClickListener = {})
    }
}