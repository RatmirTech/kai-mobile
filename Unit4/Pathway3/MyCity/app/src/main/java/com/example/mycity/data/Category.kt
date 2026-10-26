package com.example.mycity.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mycity.R

/**
 * Категории рекомендаций, которые показываются в приложении.
 */
enum class Category(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    Sights(R.string.category_sights, Icons.Default.Place),
    Parks(R.string.category_parks, Icons.Default.Park),
    Food(R.string.category_food, Icons.Default.Restaurant),
    Museums(R.string.category_museums, Icons.Default.Museum);
}
