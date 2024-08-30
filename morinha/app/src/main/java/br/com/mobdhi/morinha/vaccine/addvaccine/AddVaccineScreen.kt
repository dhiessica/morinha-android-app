package br.com.mobdhi.morinha.vaccine.addvaccine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.ui.components.DatePickerModal
import br.com.mobdhi.morinha.ui.components.DefaultButton
import br.com.mobdhi.morinha.ui.components.DefaultOutlinedButton
import br.com.mobdhi.morinha.ui.components.DefaultTextField
import br.com.mobdhi.morinha.ui.components.ErrorMessage
import br.com.mobdhi.morinha.ui.components.LoadingCircularProgress
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import br.com.mobdhi.morinha.utils.convertDateToMillis
import br.com.mobdhi.morinha.utils.convertMillisToDate


@Composable
fun AddEditVaccineScreen(
    viewModel: AddEditVaccineViewModel,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    AddEditVaccineContent(
        uiState = uiState,
        updateUiState = viewModel::updateUiState,
        onAddEditVaccineButtonClicked = viewModel::addEditVaccine,
        onAddEditVaccineWithSuccess = navigateBack
    )
}

@Composable
fun AddEditVaccineContent(
    uiState: AddVaccineUiState,
    updateUiState: (Vaccine) -> Unit,
    onAddEditVaccineButtonClicked: () -> Unit,
    onAddEditVaccineWithSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (uiState.vaccine.value.id.isNotBlank()) stringResource(R.string.edit_vaccine_title)
            else stringResource(R.string.add_vaccine_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        when (uiState) {
            is AddVaccineUiState.Initial -> {
                AddEditVaccineForm(
                    vaccine = uiState.vaccine.value,
                    isEntryValid = uiState.isEntryValid.value,
                    updateUiState = updateUiState,
                    onAddSaveVaccineButtonClicked = onAddEditVaccineButtonClicked
                )
            }

            is AddVaccineUiState.Success -> {
                onAddEditVaccineWithSuccess.invoke()
            }

            is AddVaccineUiState.Error -> {
                ErrorMessage()
            }

            is AddVaccineUiState.Loading -> {
                LoadingCircularProgress()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditVaccineForm(
    vaccine: Vaccine,
    updateUiState: (Vaccine) -> Unit,
    onAddSaveVaccineButtonClicked: () -> Unit,
    isEntryValid: Boolean
) {
    var isNextApplicationDateInput by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
    ) {
        DefaultTextField(
            label = stringResource(R.string.vaccine_name),
            value = vaccine.name,
            onValueChange = {
                updateUiState(vaccine.copy(name = it))
            }
        )

        DefaultTextField(
            label = stringResource(R.string.vaccine_manufacturer),
            value = vaccine.manufacturer,
            onValueChange = {
                updateUiState(vaccine.copy(manufacturer = it))
            }
        )

        DefaultTextField(
            label = stringResource(R.string.vaccine_batch_number),
            value = vaccine.batchNumber,
            onValueChange = {
                updateUiState(vaccine.copy(batchNumber = it))
            }
        )

        DefaultTextField(
            label = stringResource(R.string.vaccine_veterinarian),
            value = vaccine.veterinarian,
            onValueChange = {
                updateUiState(vaccine.copy(veterinarian = it))
            }
        )


        DefaultOutlinedButton(
            enabled = true,
            hasIcon = true,
            text = vaccine.applicationDate.ifBlank {
                stringResource(R.string.vaccine_application_date)
            },
            onClick = {
                showDatePicker = true
                datePickerState.selectedDateMillis = convertDateToMillis(
                    vaccine.applicationDate
                )
            }
        )

        DefaultOutlinedButton(
            enabled = true,
            hasIcon = true,
            text = vaccine.nextApplicationDate.ifBlank {
                stringResource(R.string.vaccine_next_application_date)
            },
            onClick = {
                showDatePicker = true
                isNextApplicationDateInput = true
                datePickerState.selectedDateMillis = convertDateToMillis(
                    vaccine.nextApplicationDate
                )
            }
        )

        if (showDatePicker) {
            DatePickerModal(
                datePickerState = datePickerState,
                onDismiss = {
                    datePickerState.selectedDateMillis = null
                    showDatePicker = false
                },
                onDateSelected = {
                    if (isNextApplicationDateInput) {
                        updateUiState(
                            vaccine.copy(nextApplicationDate = it?.let { convertMillisToDate(it) }
                                ?: "")
                        )
                    } else {
                        updateUiState(
                            vaccine.copy(applicationDate = it?.let { convertMillisToDate(it) }
                                ?: "")
                        )
                    }
                }
            )
        }


        DefaultTextField(
            label = stringResource(R.string.vaccine_observation),
            value = vaccine.observation,
            onValueChange = {
                updateUiState(vaccine.copy(observation = it))
            }
        )
    }

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_divisor)))
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        DefaultButton(
            text = if (vaccine.id.isNotBlank()) stringResource(R.string.save)
            else stringResource(R.string.add_button),
            enabled = isEntryValid,
            onClick = { onAddSaveVaccineButtonClicked() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddVaccineScreenPreview() {
    MorinhaTheme {
        val uiState = AddVaccineUiState.Initial(
            vaccine = remember { mutableStateOf(Vaccine()) },
            isEntryValid = remember { mutableStateOf(false) }
        )

        AddEditVaccineContent(
            uiState = uiState,
            updateUiState = {},
            onAddEditVaccineButtonClicked = {},
            onAddEditVaccineWithSuccess = {}
        )
    }
}
