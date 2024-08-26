package br.com.mobdhi.morinha.home.addpet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.domain.model.Genre
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Specie
import br.com.mobdhi.morinha.home.data.getDefaultGenres
import br.com.mobdhi.morinha.home.data.getDefaultSpecies
import br.com.mobdhi.morinha.ui.components.DatePickerModal
import br.com.mobdhi.morinha.ui.components.DefaultButton
import br.com.mobdhi.morinha.ui.components.DefaultOutlinedButton
import br.com.mobdhi.morinha.ui.components.DefaultTextField
import br.com.mobdhi.morinha.ui.components.LoadingCircularProgress
import br.com.mobdhi.morinha.ui.components.RadioButtonOption
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import br.com.mobdhi.morinha.utils.CurrencyAmountInputVisualTransformation
import br.com.mobdhi.morinha.utils.convertMillisToDate
import org.koin.androidx.compose.getViewModel

@Composable
fun AddPetScreen(
    viewModel: AddPetViewModel = getViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AddPetContent(
        uiState = uiState,
        updateUiState = viewModel::updateUiState,
        onAddPetButtonClicked = viewModel::addPet,
        onAddPetWithSuccess = navigateBack
    )
}

@Composable
fun AddPetContent(
    uiState: AddPetUiState,
    updateUiState: (Pet) -> Unit,
    onAddPetButtonClicked: () -> Unit,
    onAddPetWithSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.add_pet_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        when(uiState) {
            is AddPetUiState.Initial -> {
                AddPetForm(
                    pet = uiState.pet.value,
                    specieOptions = getDefaultSpecies(),
                    genreOptions = getDefaultGenres(),
                    updateUiState = updateUiState,
                    onAddPetButtonClicked = onAddPetButtonClicked
                )
            }
            is AddPetUiState.Success -> {
                onAddPetWithSuccess.invoke()
            }
            is AddPetUiState.Error -> {
                AddPetError()
            }
            is AddPetUiState.Loading -> {
                LoadingCircularProgress()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetForm(
    pet: Pet,
    specieOptions: List<Specie>,
    genreOptions: List<Genre>,
    updateUiState: (Pet) -> Unit,
    onAddPetButtonClicked: () -> Unit
) {
    //TODO: campos nao estao alterando o valor quando digita no campo
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    val selectedDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
    ) {
        DefaultTextField(
            label = stringResource(R.string.pet_name),
            value = pet.name,
            onValueChange = {
                updateUiState(pet.copy(name = it))
            }
        )
        DefaultTextField(
            label = stringResource(R.string.pet_breed),
            value = pet.breed,
            onValueChange = { updateUiState(pet.copy(breed = it)) }
        )
        DefaultTextField(
            label = stringResource(R.string.pet_weight),
            value = pet.weight.ifBlank { "" }.replace(".",""),
            onValueChange = {
                if (it.isDigitsOnly() && it.length <= 4) {
                    updateUiState(
                        pet.copy(
                            weight = if (it.startsWith("0") || it.startsWith(" ")) {
                                ""
                            } else {
                                addDotBeforeLastNumber(it)
                            }
                        )
                    )
                }
            },
            visualTransformation = CurrencyAmountInputVisualTransformation(
                fixedCursorAtTheEnd = true,
                numberOfDecimals = 1
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            suffix = { Text ("kg") }

        )
        DefaultTextField(
            label = stringResource(R.string.pet_color),
            value = pet.color,
            onValueChange = { updateUiState(pet.copy(color = it)) }
        )
        DefaultOutlinedButton(
            text = selectedDate.ifBlank { stringResource(R.string.pet_born_date) },
            enabled = true,
            hasIcon = true,
            onClick = { showDatePicker = true }
        )

        if (showDatePicker) {
            DatePickerModal(
                datePickerState = datePickerState,
                onDateSelected = { },
                onDismiss = {
                    showDatePicker = false
                    updateUiState(pet.copy(bornDate = selectedDate))
                }
            )
        }

        Row {
            Column {
                RadioButtonOption(
                    text = specieOptions.first().name,
                    selected = pet.specie.id == specieOptions.first().id,
                    onSelected = {
                        updateUiState(pet.copy(specie = specieOptions.first()))
                    }
                )
                RadioButtonOption(
                    text = genreOptions.first().name,
                    selected = pet.genre.id == genreOptions.first().id,
                    onSelected = {
                        updateUiState(pet.copy(genre = genreOptions.first()))
                    }
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                RadioButtonOption(
                    text = specieOptions.last().name,
                    selected = pet.specie.id == specieOptions.last().id,
                    onSelected = {
                        updateUiState(pet.copy(specie = specieOptions.first()))
                    }
                )
                RadioButtonOption(
                    text = genreOptions.last().name,
                    selected = pet.genre.id == genreOptions.last().id,
                    onSelected = {
                        updateUiState(pet.copy(genre = genreOptions.last()))
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_divisor)))
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        DefaultButton(
            text = stringResource(R.string.add_button),
            enabled = true,
            onClick = { onAddPetButtonClicked() }
        )
    }
}

@Composable
fun AddPetError() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = stringResource(R.string.content_arrow_right_icon),
            modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
        )
        Text(
            text = stringResource(R.string.add_pet_error),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.add_pet_error_sub),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

fun addDotBeforeLastNumber(input: String): String {
    val regex = Regex("(\\d)(?=\\D*$)")
    return regex.replace(input) { matchResult ->
        ".${matchResult.value}"
    }
}



@Preview(showSystemUi = true)
@Composable
fun AddPetScreenPreview() {
    MorinhaTheme {

    }
}