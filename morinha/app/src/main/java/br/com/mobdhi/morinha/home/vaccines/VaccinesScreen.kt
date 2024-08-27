package br.com.mobdhi.morinha.home.vaccines

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.domain.model.Vaccine
import br.com.mobdhi.morinha.ui.components.PetCard
import br.com.mobdhi.morinha.ui.components.TimelineNodePosition
import br.com.mobdhi.morinha.ui.components.VaccineItem
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme

@Composable
fun VaccinesScreen() {

    LaunchedEffect(Unit) { /*TODO*/ }

    VaccinesContent()
}
@Composable
fun VaccinesContent() {
    val name = remember { mutableStateOf("Amora") }
    val breed = remember { mutableStateOf("Yorkshire Terrier") }
    val weight = remember { mutableStateOf("4.3") }
    val age = remember { mutableStateOf("3") }

    val vaccinesList = listOf(Vaccine())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name.value,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row {
                    Text(
                        text = breed.value,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                    Text(
                        text = "${weight.value} kg",
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = stringResource(R.string.x_years_old, age.value),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.content_add),
                    modifier = Modifier.size(dimensionResource(R.dimen.padding_large))
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_divisor)))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
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
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun VaccinesScreenPreview() {
    MorinhaTheme {
        VaccinesScreen()
    }
}