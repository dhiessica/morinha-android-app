package br.com.mobdhi.morinha.vaccine.vaccines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.ui.components.ErrorMessage
import br.com.mobdhi.morinha.ui.components.LoadingCircularProgress
import br.com.mobdhi.morinha.ui.components.TimelineNodePosition
import br.com.mobdhi.morinha.ui.components.VaccineItem
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import br.com.mobdhi.morinha.utils.calculateAgeFromDate
import org.koin.androidx.compose.getViewModel

@Composable
fun VaccinesScreen(
    pet: Pet,
    viewModel: VaccinesViewModel = getViewModel(),
    navigateToEditPetScreen: (Pet) -> Unit,
    navigateToAddVaccineScreen: (String) -> Unit,
    navigateToVaccineEditScreen: (Vaccine) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.getVaccines(pet) }

    VaccinesContent(
        pet = pet,
        uiState = uiState,
        onEditPetButtonClicked = navigateToEditPetScreen,
        onAddVaccineButtonClicked = navigateToAddVaccineScreen,
        onVaccineItemClicked = navigateToVaccineEditScreen
    )
}


@Composable
fun VaccinesContent(
    pet: Pet,
    uiState: VaccinesUiState,
    onEditPetButtonClicked: (Pet) -> Unit,
    onAddVaccineButtonClicked: (String) -> Unit,
    onVaccineItemClicked: (Vaccine) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        PetInfoHeader(
            pet = pet,
            onEditPetButtonClicked = onEditPetButtonClicked
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(
                    top = dimensionResource(R.dimen.padding_large),
                    start = dimensionResource(R.dimen.padding_large),
                    end = dimensionResource(R.dimen.padding_large),
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.vaccines),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = { onAddVaccineButtonClicked(pet.id) }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.content_add),
                        modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
                    )
                }
            }

            when(uiState) {
                is VaccinesUiState.Success -> {
                    if (!uiState.vaccinesList.isNullOrEmpty()) {
                        LazyColumn(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items = uiState.vaccinesList) { item ->
                                VaccineItem(
                                    position = when (item) {
                                        uiState.vaccinesList.first() -> TimelineNodePosition.FIRST
                                        uiState.vaccinesList.last() -> TimelineNodePosition.LAST
                                        else -> TimelineNodePosition.MIDDLE
                                    },
                                    name = item.name,
                                    applicationDate = item.applicationDate,
                                    onClick = { onVaccineItemClicked(item) }
                                )
                            }
                        }
                    } else EmptyMessage()
                }

                is VaccinesUiState.Error -> {
                    ErrorMessage()
                }

                is VaccinesUiState.Loading -> {
                    LoadingCircularProgress()
                }
            }
        }
    }
}

@Composable
fun PetInfoHeader(
    pet: Pet,
    onEditPetButtonClicked: (Pet) -> Unit
) {
    val petAge = calculateAgeFromDate(pet.bornDate)

    Column(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Row {
                    Text(
                        text = pet.breed,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                    Text(
                        text = "${pet.weight} kg",
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Text(
                    text = if(petAge >= 0) stringResource(R.string.x_years_old, petAge)
                    else pet.bornDate,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { onEditPetButtonClicked(pet) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.content_add),
                    modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
}

@Composable
fun EmptyMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = stringResource(R.string.content_error_icon),
            modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Text(
            text = stringResource(R.string.empty_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}
@Preview(showSystemUi = true)
@Composable
fun VaccinesScreenPreview() {
    MorinhaTheme {
        val uiState  = VaccinesUiState.Success(listOf())

        VaccinesContent(
            pet = Pet(
                name = "Amora",
                breed = "Yorkshire Terrier",
                bornDate = "07/06/2021"
            ),
            uiState = uiState,
            onEditPetButtonClicked = {},
            onAddVaccineButtonClicked = {},
            onVaccineItemClicked = {}
        )
    }
}