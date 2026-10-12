package com.example.thirtydays.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Day(
    val number: Int,
    @StringRes val titleRes: Int,
    @StringRes val detailRes: Int,
    @DrawableRes val imageRes: Int
)
