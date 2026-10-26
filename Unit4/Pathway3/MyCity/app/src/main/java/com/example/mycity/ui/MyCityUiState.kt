package com.example.mycity.ui

import com.example.mycity.data.Category
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.data.Place

data class MyCityUiState(
    val currentCategory: Category = Category.Sights,
    val currentPlace: Place = LocalPlacesDataProvider.getPlacesByCategory(Category.Sights).first()
) {
    val places: List<Place>
        get() = LocalPlacesDataProvider.getPlacesByCategory(currentCategory)
}
