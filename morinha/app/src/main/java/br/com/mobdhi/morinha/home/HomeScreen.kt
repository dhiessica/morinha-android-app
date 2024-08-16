package br.com.mobdhi.morinha.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobdhi.morinha.R
import br.com.mobdhi.morinha.domain.model.Pet
import br.com.mobdhi.morinha.ui.components.PetCard
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        modifier = Modifier
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    modifier: Modifier
) {

    //Todo add swipe refresh
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(R.dimen.padding_large),
                end = dimensionResource(R.dimen.padding_large),
                top = dimensionResource(R.dimen.padding_large)
            ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
        Text(
            text = stringResource(R.string.pets_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        when(uiState) {
            is HomeUiState.Loading -> {
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
            is HomeUiState.Success -> {
                if (!uiState.petList.isNullOrEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxSize()
                    ) {
                        items(items = uiState.petList) { item ->
                            PetCard(
                                petName = item.name,
                                petBasicInfo = item.breed,
                                onClick = {}
                            )
                        }
                    }
                }
            }
            is HomeUiState.Error -> {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MorinhaTheme {
        HomeScreen()
    }
}