package br.com.mobdhi.morinha.pet.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.ui.components.ErrorMessage
import br.com.mobdhi.morinha.ui.components.LoadingCircularProgress
import br.com.mobdhi.morinha.ui.components.PetCard
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun PetsScreen(
    viewModel: PetsViewModel = getViewModel(),
    navigateToAddPetScreen: () -> Unit,
    navigateToPetVaccinesScreen: (Pet) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.getPets() }

    PetsContent(
        uiState = uiState,
        onAddPetButtonClicked = navigateToAddPetScreen,
        onPetCardClicked = navigateToPetVaccinesScreen,
        modifier = Modifier
    )
}

@Composable
fun PetsContent(
    uiState: PetsUiState,
    onAddPetButtonClicked: () -> Unit,
    onPetCardClicked: (Pet) -> Unit,
    modifier: Modifier
) {

    //Todo add swipe refresh
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        Row {
            Text(
                text = stringResource(R.string.pets_title),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onAddPetButtonClicked) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.content_add),
                    modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
                )

            }
        }
        when(uiState) {
            is PetsUiState.Success -> {
                if (!uiState.petList.isNullOrEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxSize()
                    ) {
                        items(items = uiState.petList) { pet ->
                            PetCard(
                                petName = pet.name,
                                petBasicInfo = pet.breed,
                                onClick = { onPetCardClicked(pet) }
                            )
                        }
                    }
                }
            }
            is PetsUiState.Error -> {
                ErrorMessage()
            }
            is PetsUiState.Loading -> {
                LoadingCircularProgress()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MorinhaTheme {
        PetsContent(
            uiState = PetsUiState.Success(listOf(Pet())),
            onPetCardClicked = {},
            onAddPetButtonClicked = {},
            modifier = Modifier
        )
    }
}