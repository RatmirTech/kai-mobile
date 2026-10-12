package com.example.thirtydays.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = CutCornerShape(topEnd = 24.dp, bottomStart = 24.dp),
    large = RoundedCornerShape(16.dp)
)
