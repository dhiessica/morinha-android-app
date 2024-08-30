package br.com.mobdhi.morinha.home.vaccines

import android.content.res.Resources.Theme
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
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EditAttributes
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.ui.components.PetCard
import br.com.mobdhi.morinha.ui.components.TimelineNodePosition
import br.com.mobdhi.morinha.ui.components.VaccineItem
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import br.com.mobdhi.morinha.ui.theme.PurpleGrey80
import br.com.mobdhi.morinha.utils.calculateAgeFromDate

@Composable
fun VaccinesScreen(pet: Pet) {

    LaunchedEffect(Unit) { /*TODO*/ }

    val vaccinesList = listOf(
        Vaccine(name = "Vaccine name", applicationDate = "11/11/2011"),
        Vaccine(name = "Vaccine name", applicationDate = "11/11/2011")
    )

    VaccinesContent(
        pet = pet,
        vaccinesList = vaccinesList,
        onEditPetButtonClicked = {},
        onAddVaccineButtonClicked = {},
        onVaccineItemClicked = {}
    )
}
@Composable
fun VaccinesContent(
    pet: Pet,
    vaccinesList: List<Vaccine>,
    onEditPetButtonClicked: () -> Unit,
    onAddVaccineButtonClicked: () -> Unit,
    onVaccineItemClicked: (Vaccine) -> Unit
) {
    val petAge = calculateAgeFromDate(pet.bornDate)

    Column(modifier = Modifier.fillMaxSize()) {
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
                    Text(
                        text = if(petAge >= 0) stringResource(R.string.x_years_old, petAge)
                        else pet.bornDate,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(onClick = { onEditPetButtonClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.content_add),
                        modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.Gray)
                .padding(
                    top = dimensionResource(R.dimen.padding_large),
                    start = dimensionResource(R.dimen.padding_large),
                    end = dimensionResource(R.dimen.padding_large)
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
                IconButton(onClick = { onAddVaccineButtonClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.content_add),
                        modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
                    )
                }
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = vaccinesList) { item ->
                    VaccineItem(
                        position = when (item) {
                            vaccinesList.first() -> TimelineNodePosition.FIRST
                            vaccinesList.last() -> TimelineNodePosition.LAST
                            else -> TimelineNodePosition.MIDDLE
                        },
                        name = item.name,
                        applicationDate = item.applicationDate,
                        onClick = { onVaccineItemClicked(item) }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun VaccinesScreenPreview() {
    MorinhaTheme {
        VaccinesScreen(Pet())
    }
}