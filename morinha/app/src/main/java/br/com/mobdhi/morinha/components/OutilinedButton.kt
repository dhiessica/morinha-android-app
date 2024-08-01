package br.com.mobdhi.morinha.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme

@Composable
fun OutlinedButton2(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    hasIcon: Boolean = false
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, primaryColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = primaryColor,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        if (hasIcon) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1F)
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.content_arrow_right_icon),
                modifier = Modifier.size(24.dp)
            )
        }
        else
            Text(
                text = text,
                modifier = Modifier.padding(8.dp)
            )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedButtonPreview() {
    MorinhaTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
        ) {
            OutlinedButton2(
                text = "teste",
                enabled = true,
                onClick = {},
                hasIcon = true
            )
        }
    }
}