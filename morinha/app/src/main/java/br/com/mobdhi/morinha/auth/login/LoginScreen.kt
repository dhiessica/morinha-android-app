package br.com.mobdhi.morinha.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.ui.components.DefaultButton
import br.com.mobdhi.morinha.ui.components.DefaultTextField
import br.com.mobdhi.morinha.ui.components.DefaultOutlinedButton
import br.com.mobdhi.morinha.ui.components.PasswordTextField
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = getViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginContent(
        uiState = uiState,
        email = uiState.email,
        password = uiState.password,
        onEmailValueChanged = viewModel::onEmailChange,
        onPasswordValueChanged = viewModel::onPasswordChange,
        onEnterClicked = viewModel::loginUser,
        onForgotPasswordClicked = navigateToForgotPasswordScreen,
        onRegisterClicked = navigateToRegisterScreen,
        onLoginWithSuccess = navigateToHomeScreen
    )
}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    email: String,
    password: String,
    onEmailValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onEnterClicked: (String, String) -> Unit,
    onRegisterClicked: () -> Unit,
    onLoginWithSuccess: () -> Unit
) {
    if (uiState.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_large)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
    else if (uiState.isSuccess) {
        onLoginWithSuccess.invoke()
    }
    else {
        if (uiState.isError.isNotEmpty()) {
            Toast.makeText(
                LocalContext.current,
                "Não foi possível efetuar a entrada",
                Toast.LENGTH_LONG
            ).show()
            Log.e("Login", uiState.isError)
        }
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
                    text = stringResource(R.string.log_in_title),
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
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable(
                                onClick = onForgotPasswordClicked
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_divisor)))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                DefaultButton(
                    text = stringResource(R.string.log_in),
                    enabled = true,
                    onClick = { onEnterClicked(email, password) }
                )
                DefaultOutlinedButton(
                    text = stringResource(R.string.register),
                    enabled = true,
                    onClick = onRegisterClicked
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MorinhaTheme {
        LoginScreen(
            navigateToHomeScreen = {},
            navigateToForgotPasswordScreen = {},
            navigateToRegisterScreen = {}
        )
    }
}