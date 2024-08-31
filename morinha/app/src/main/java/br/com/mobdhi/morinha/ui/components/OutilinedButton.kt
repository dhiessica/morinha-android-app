package br.com.mobdhi.morinha.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme

@Composable
fun DefaultOutlinedButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    hasIcon: Boolean = false,
    isLoading: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = { if(!isLoading) onClick() },
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, color),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = color,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(24.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            if (hasIcon) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .weight(1F)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.content_arrow_right_icon),
                    modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
                )
            }
            else
                Text(
                    text = text,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedButtonPreview() {
    MorinhaTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
        ) {
            DefaultOutlinedButton(
                text = "teste",
                enabled = true,
                onClick = {},
                hasIcon = true
            )
        }
    }
}