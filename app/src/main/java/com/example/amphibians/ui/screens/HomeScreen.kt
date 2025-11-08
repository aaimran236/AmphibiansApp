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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AmphibianUiState.Success -> AmphibianListScreen(amphibianUiState.amphibians, modifier = modifier.fillMaxWidth(), contentPadding = contentPadding)
        is AmphibianUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
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
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(amphibians, key = {amphibian-> amphibian.name}){
            amphibian->
            AmphibianInfoCard(
                amphibianInfo = amphibian,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun AmphibianInfoCard( amphibianInfo: AmphibianInfo,modifier: Modifier= Modifier){
    Card(
        modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(5.dp),
        ///elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(

        ) {
            Text(
                text = stringResource(R.string.amphibian_name_with_type,
                    amphibianInfo.name,amphibianInfo.type),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibianInfo.imageSource)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.amphibian_photo),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                modifier = Modifier.fillMaxWidth()
                    ///.height(200.dp),
                    .aspectRatio(1.5f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = amphibianInfo.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            )
        }
    }
}

/**
 * The home screen displaying the loading message.
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

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
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
       ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibianListScreenPreview(){
    AmphibiansTheme {
        val mockData = List(5){ AmphibianInfo(name = "$it", type = "", description = "", imageSource = "") }
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