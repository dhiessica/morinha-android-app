package br.com.mobdhi.morinha.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.components.DefaultButton
import br.com.mobdhi.morinha.components.DefaultTextField
import br.com.mobdhi.morinha.components.OutlinedButton2
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        ) {
            DefaultTextField(
                value = stringResource(R.string.email),
                onValueChange = {}
            )

            DefaultTextField(
                value = stringResource(R.string.password),
                onValueChange = {}
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable(
                            onClick = { }
                        )
                        .align(Alignment.CenterEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_divisor)))
        DefaultButton(
            text = stringResource(R.string.sign_in),
            enabled = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        OutlinedButton2(
            text = stringResource(R.string.register),
            enabled = true,
            onClick = { /*TODO*/ }
        )




    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MorinhaTheme {
        LoginScreen()
    }
}