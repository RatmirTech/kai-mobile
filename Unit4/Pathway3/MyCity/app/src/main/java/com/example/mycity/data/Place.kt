package com.example.mycity.data

import androidx.annotation.StringRes

/**
 * Рекомендуемое место или блюдо.
 */
data class Place(
    val id: Int,
    val category: Category,
    @StringRes val name: Int,
    @StringRes val description: Int,
    @StringRes val location: Int
)
