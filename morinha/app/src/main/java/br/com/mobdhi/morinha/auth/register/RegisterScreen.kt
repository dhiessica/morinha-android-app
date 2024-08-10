package br.com.mobdhi.morinha.auth.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.ui.components.ConfirmPasswordTextField
import br.com.mobdhi.morinha.ui.components.DefaultButton
import br.com.mobdhi.morinha.ui.components.DefaultTextField
import br.com.mobdhi.morinha.ui.components.PasswordTextField
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = getViewModel(),
    navigateToHomeScreen: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterContent(
        uiState = uiState,
        email = uiState.email,
        password = uiState.password,
        confirmPassword = uiState.confirmPassword,
        onEmailValueChanged = viewModel::onEmailChange,
        onPasswordValueChanged = viewModel::onPasswordChange,
        onConfirmPasswordValueChanged = viewModel::onConfirmPasswordChange,
        onRegisterClicked = viewModel::registerUser,
        onRegisterWithSuccess = navigateToHomeScreen
    )
}

@Composable
fun RegisterContent(
    uiState: RegisterUiState,
    email: String,
    password: String,
    confirmPassword: String,
    onEmailValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    onConfirmPasswordValueChanged: (String) -> Unit,
    onRegisterClicked: (String, String) -> Unit,
    onRegisterWithSuccess: () -> Unit
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
        onRegisterWithSuccess.invoke()
    } else {
        if (uiState.isError.isNotEmpty()) {
            Toast.makeText(LocalContext.current, "Não foi possível efetuar o cadastro", Toast.LENGTH_LONG).show()
            Log.e("Register", uiState.isError)
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
                    onPasswordChange = onPasswordValueChanged,
                    minLength = 6
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
                    onClick = { onRegisterClicked(email, password) }
                )
            }
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