package com.example.mycity.data

import com.example.mycity.R

/**
 * Локальный источник данных с рекомендациями по городу.
 */
object LocalPlacesDataProvider {

    val allPlaces: List<Place> = listOf(
        Place(
            id = 1,
            category = Category.Sights,
            name = R.string.place_kremlin_name,
            description = R.string.place_kremlin_description,
            location = R.string.place_kremlin_location
        ),
        Place(
            id = 2,
            category = Category.Sights,
            name = R.string.place_kulsharif_name,
            description = R.string.place_kulsharif_description,
            location = R.string.place_kulsharif_location
        ),
        Place(
            id = 3,
            category = Category.Sights,
            name = R.string.place_bauman_name,
            description = R.string.place_bauman_description,
            location = R.string.place_bauman_location
        ),
        Place(
            id = 4,
            category = Category.Sights,
            name = R.string.place_allreligions_name,
            description = R.string.place_allreligions_description,
            location = R.string.place_allreligions_location
        ),
        Place(
            id = 5,
            category = Category.Sights,
            name = R.string.place_farmers_name,
            description = R.string.place_farmers_description,
            location = R.string.place_farmers_location
        ),
        Place(
            id = 6,
            category = Category.Parks,
            name = R.string.place_gorky_name,
            description = R.string.place_gorky_description,
            location = R.string.place_gorky_location
        ),
        Place(
            id = 7,
            category = Category.Parks,
            name = R.string.place_kaban_name,
            description = R.string.place_kaban_description,
            location = R.string.place_kaban_location
        ),
        Place(
            id = 8,
            category = Category.Parks,
            name = R.string.place_millennium_name,
            description = R.string.place_millennium_description,
            location = R.string.place_millennium_location
        ),
        Place(
            id = 9,
            category = Category.Parks,
            name = R.string.place_blacklake_name,
            description = R.string.place_blacklake_description,
            location = R.string.place_blacklake_location
        ),
        Place(
            id = 10,
            category = Category.Parks,
            name = R.string.place_lyadskoy_name,
            description = R.string.place_lyadskoy_description,
            location = R.string.place_lyadskoy_location
        ),
        Place(
            id = 11,
            category = Category.Food,
            name = R.string.place_echpochmak_name,
            description = R.string.place_echpochmak_description,
            location = R.string.place_echpochmak_location
        ),
        Place(
            id = 12,
            category = Category.Food,
            name = R.string.place_chakchak_name,
            description = R.string.place_chakchak_description,
            location = R.string.place_chakchak_location
        ),
        Place(
            id = 13,
            category = Category.Food,
            name = R.string.place_gubadia_name,
            description = R.string.place_gubadia_description,
            location = R.string.place_gubadia_location
        ),
        Place(
            id = 14,
            category = Category.Food,
            name = R.string.place_belesh_name,
            description = R.string.place_belesh_description,
            location = R.string.place_belesh_location
        ),
        Place(
            id = 15,
            category = Category.Food,
            name = R.string.place_kystyby_name,
            description = R.string.place_kystyby_description,
            location = R.string.place_kystyby_location
        ),
        Place(
            id = 16,
            category = Category.Museums,
            name = R.string.place_nationalmuseum_name,
            description = R.string.place_nationalmuseum_description,
            location = R.string.place_nationalmuseum_location
        ),
        Place(
            id = 17,
            category = Category.Museums,
            name = R.string.place_islamic_name,
            description = R.string.place_islamic_description,
            location = R.string.place_islamic_location
        ),
        Place(
            id = 18,
            category = Category.Museums,
            name = R.string.place_chakchakmuseum_name,
            description = R.string.place_chakchakmuseum_description,
            location = R.string.place_chakchakmuseum_location
        ),
        Place(
            id = 19,
            category = Category.Museums,
            name = R.string.place_artmuseum_name,
            description = R.string.place_artmuseum_description,
            location = R.string.place_artmuseum_location
        )
    )

    fun getPlacesByCategory(category: Category): List<Place> =
        allPlaces.filter { it.category == category }

    val defaultPlace: Place = allPlaces.first()
}
