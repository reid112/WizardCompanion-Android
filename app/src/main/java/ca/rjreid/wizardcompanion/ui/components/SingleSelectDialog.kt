package ca.rjreid.wizardcompanion.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme
import ca.rjreid.wizardcompanion.ui.theme.spacing

@Composable
fun SingleSelectDialog(
    title: String,
    optionsList: List<String>,
    defaultSelected: Int,
    onOptionSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedOption by remember { mutableStateOf(defaultSelected)}
    val listState = rememberLazyListState()

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
                Text(text = title)
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                LazyColumn(state = listState) {
                    items(items = optionsList) {
                        RadioButton(it, optionsList[selectedOption]) { selectedValue ->
                            selectedOption = optionsList.indexOf(selectedValue)
                            onOptionSelected(selectedOption)
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun SingleSelectDialogPreview() {
    WizardCompanionTheme {
        SingleSelectDialog(
            title = "Title",
            optionsList = listOf("item 1", "item 2", "item 2"),
            defaultSelected = 1,
            onOptionSelected = { },
            onDismissRequest = { }
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SingleSelectDialogDarkPreview() {
    WizardCompanionTheme {
        SingleSelectDialog(
            title = "Title",
            optionsList = listOf("item 1", "item 2", "item 2"),
            defaultSelected = 1,
            onOptionSelected = { },
            onDismissRequest = { }
        )
    }
}