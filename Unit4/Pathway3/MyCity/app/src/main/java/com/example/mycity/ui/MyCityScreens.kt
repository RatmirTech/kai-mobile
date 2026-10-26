package com.example.mycity.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.Category
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.data.Place
import com.example.mycity.ui.theme.MyCityTheme

/**
 * Экран со списком категорий. Показывается только на компактных экранах,
 * на больших экранах категории находятся в navigation rail / drawer.
 */
@Composable
fun CategoryListScreen(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(categories, key = { it.name }) { category ->
            ListCard(
                icon = { CardIcon(category) },
                title = stringResource(category.title),
                subtitle = null,
                selected = false,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

/**
 * Экран со списком рекомендаций выбранной категории.
 */
@Composable
fun PlaceListScreen(
    places: List<Place>,
    selectedPlace: Place?,
    onPlaceClick: (Place) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(places, key = { it.id }) { place ->
            ListCard(
                icon = { CardIcon(place.category) },
                title = stringResource(place.name),
                subtitle = stringResource(place.location),
                selected = place == selectedPlace,
                onClick = { onPlaceClick(place) }
            )
        }
    }
}

/**
 * Экран с подробным описанием выбранного места.
 */
@Composable
fun PlaceDetailScreen(
    place: Place,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(dimensionResource(R.dimen.detail_icon_container_size))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = place.category.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(dimensionResource(R.dimen.detail_icon_size))
                )
            }
        }
        Text(
            text = stringResource(place.name),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.location_caption, stringResource(place.location)),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = stringResource(place.description),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Список и детали рядом для расширенных экранов.
 */
@Composable
fun PlaceListAndDetailScreen(
    places: List<Place>,
    selectedPlace: Place,
    onPlaceClick: (Place) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Row(modifier = modifier) {
        PlaceListScreen(
            places = places,
            selectedPlace = selectedPlace,
            onPlaceClick = onPlaceClick,
            contentPadding = contentPadding,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
        PlaceDetailScreen(
            place = selectedPlace,
            contentPadding = contentPadding,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(end = dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
private fun CardIcon(category: Category, modifier: Modifier = Modifier) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier.size(dimensionResource(R.dimen.list_icon_container_size))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(dimensionResource(R.dimen.list_icon_size))
            )
        }
    }
}

@Composable
private fun ListCard(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String?,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            icon()
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryListScreenPreview() {
    MyCityTheme {
        CategoryListScreen(
            categories = Category.entries,
            onCategoryClick = {},
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaceListScreenPreview() {
    MyCityTheme {
        PlaceListScreen(
            places = LocalPlacesDataProvider.getPlacesByCategory(Category.Sights),
            selectedPlace = null,
            onPlaceClick = {},
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaceDetailScreenPreview() {
    MyCityTheme {
        PlaceDetailScreen(
            place = LocalPlacesDataProvider.defaultPlace,
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
private fun PlaceListAndDetailScreenPreview() {
    MyCityTheme {
        PlaceListAndDetailScreen(
            places = LocalPlacesDataProvider.getPlacesByCategory(Category.Sights),
            selectedPlace = LocalPlacesDataProvider.defaultPlace,
            onPlaceClick = {}
        )
    }
}
