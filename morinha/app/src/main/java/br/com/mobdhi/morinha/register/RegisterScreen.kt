package br.com.mobdhi.morinha.register

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.components.ConfirmPasswordTextField
import br.com.mobdhi.morinha.components.DefaultButton
import br.com.mobdhi.morinha.components.DefaultOutlinedButton
import br.com.mobdhi.morinha.components.DefaultTextField
import br.com.mobdhi.morinha.components.PasswordTextField
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme

@Composable
fun RegisterScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    RegisterContent(
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        onEmailValueChanged = { email = it },
        onPasswordValueChanged = { password = it },
        onConfirmPasswordValueChanged = { confirmPassword = it },
        onForgotPasswordClicked = { /*TODO*/ },
        onEnterClicked = { /*TODO*/ },
        onRegisterClicked = {}
    )
}

@Composable
fun RegisterContent(
    email: String,
    password: String,
    confirmPassword: String,
    onEmailValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    onConfirmPasswordValueChanged: (String) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onEnterClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        ) {
            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            DefaultTextField(
                label = stringResource(R.string.email),
                value = email,
                onValueChange = onEmailValueChanged
            )
            PasswordTextField(
                label = stringResource(R.string.password),
                password = password,
                onPasswordChange = onPasswordValueChanged
            )
            ConfirmPasswordTextField(
                password = password,
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = onConfirmPasswordValueChanged
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_divisor)))
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            DefaultButton(
                text = stringResource(R.string.continue_button),
                enabled = true,
                onClick = onEnterClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    MorinhaTheme {
        RegisterScreen()
    }
}