package br.com.mobdhi.morinha.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.auth.login.LoginScreen
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme

@Composable
fun DefaultTextField(
    label: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    maxCharacters: Int = 100
) {
    TextField(
        label = { Text(label) },
        value = value,
        onValueChange = {
            if (it.length <= maxCharacters) {
                onValueChange(it)
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultTextFieldPreview() {
    MorinhaTheme {
        var text by remember { mutableStateOf("") }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large))
        ) {
            DefaultTextField(
                value = text,
                onValueChange = { text = it }
            )
        }
    }
}