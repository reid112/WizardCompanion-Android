package ca.rjreid.wizardcompanion.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness1
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ca.rjreid.wizardcompanion.R
import ca.rjreid.wizardcompanion.ui.theme.WizardCompanionTheme

@Composable
fun SeparatorDot(
    modifier: Modifier = Modifier,
    size: Dp = 8.dp,
    tint: Color = MaterialTheme.colors.secondary
) {
    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Brightness1,
            contentDescription = stringResource(id = R.string.content_description_dot),
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}

@Preview
@Composable
fun SeparatorDotPreview() {
    WizardCompanionTheme {
        SeparatorDotPreview()
    }
}