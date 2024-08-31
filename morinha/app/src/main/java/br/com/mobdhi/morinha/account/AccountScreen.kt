package br.com.mobdhi.morinha.account

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.auth.login.LoginUiState
import br.com.mobdhi.morinha.auth.login.LoginViewModel
import br.com.mobdhi.morinha.domain.model.User
import br.com.mobdhi.morinha.pet.addeditpet.AddPetForm
import br.com.mobdhi.morinha.pet.addeditpet.AddPetUiState
import br.com.mobdhi.morinha.pet.data.getDefaultGenres
import br.com.mobdhi.morinha.pet.data.getDefaultSpecies
import br.com.mobdhi.morinha.ui.components.DefaultButton
import br.com.mobdhi.morinha.ui.components.DefaultOutlinedButton
import br.com.mobdhi.morinha.ui.components.DefaultTextField
import br.com.mobdhi.morinha.ui.components.ErrorMessage
import br.com.mobdhi.morinha.ui.components.LoadingCircularProgress
import br.com.mobdhi.morinha.ui.components.PasswordTextField
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = getViewModel(),
    logOut: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AccountContent(
        uiState = uiState,
        onLogoutWithSuccess = logOut,
        onLogoutButtonClicked = viewModel::logoutUser
    )
}

@Composable
fun AccountContent(
    uiState: AccountUiState,
    onLogoutButtonClicked: () -> Unit,
    onLogoutWithSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        Text(
            text = stringResource(R.string.account_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        when (uiState) {
            is AccountUiState.Initial -> {
                Text(
                    text = uiState.user.email,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            }

            is AccountUiState.Success -> {
                onLogoutWithSuccess.invoke()
            }

            is AccountUiState.Error -> {
                ErrorMessage()
            }

            is AccountUiState.Loading -> {
                LoadingCircularProgress()
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        DefaultOutlinedButton(
            color = MaterialTheme.colorScheme.error,
            enabled = true,
            hasIcon = false,
            text = stringResource(R.string.log_out),
            onClick = { onLogoutButtonClicked() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountContentPreview() {
    val uiState = AccountUiState.Loading()

    MorinhaTheme {
        AccountContent(
            uiState = uiState,
            onLogoutWithSuccess = {},
            onLogoutButtonClicked = {}
        )
    }
}