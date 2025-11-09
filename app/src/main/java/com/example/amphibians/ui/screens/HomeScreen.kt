package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.amphibians.model.AmphibianInfo
import com.example.amphibians.ui.theme.AmphibiansTheme
import kotlin.collections.List

@Composable
fun HomeScreen(
    amphibianUiState: AmphibianUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AmphibianUiState.Success -> AmphibianListScreen(
            amphibianUiState.amphibians,
            modifier = modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
            contentPadding = contentPadding
        )

//        For loading single AmphibianInfo card
//        is AmphibianUiState.Success -> {
//            LazyColumn(
//                modifier = Modifier.fillMaxWidth(),
//                contentPadding = contentPadding // Apply padding from the Scaffold
//            ) {
//                item{
//                    AmphibianInfoCard(amphibianUiState.amphibians, modifier = modifier.fillMaxWidth())
//                }
//            }
//        }
        is AmphibianUiState.Error -> ErrorScreen(retryAction,modifier = modifier.fillMaxSize())
    }
}

/*
 * Composable for showing Amphibian lists
 */
@Composable
fun AmphibianListScreen(
    amphibians: List<AmphibianInfo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(amphibians, key = { amphibian -> amphibian.name }) { amphibian ->
            AmphibianInfoCard(
                amphibianInfo = amphibian,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun AmphibianInfoCard(amphibianInfo: AmphibianInfo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    R.string.amphibian_name_with_type,
                    amphibianInfo.name, amphibianInfo.type
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            ///Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibianInfo.imageSource)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.amphibian_photo),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier
                    .fillMaxWidth()

            )

            ///Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = amphibianInfo.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

/**
 * The home screen displaying the loading image drawable.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        contentScale = ContentScale.Crop
    )
}

/**
 * The home screen displaying error message
 */
@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))

        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AmphibiansTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AmphibiansTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibianListScreenPreview() {
    AmphibiansTheme {
        val mockData =
            List(5) { AmphibianInfo(name = "$it", type = "", description = "", imageSource = "") }
        AmphibianListScreen(
            mockData
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AmphibianInfoCardPreview() {
    val fakeAmphibian = AmphibianInfo(
        name = "Great Crested Newt",
        type = "Newt",
        description = "The great crested newt is a large newt, and the largest native newt species in Great Britain. It is dark brown or black with a 'warty' skin, and the underside is bright orange with irregular black spots.",
        imageSource = ""
    )
    AmphibiansTheme {
        AmphibianInfoCard(amphibianInfo = fakeAmphibian)
    }
}