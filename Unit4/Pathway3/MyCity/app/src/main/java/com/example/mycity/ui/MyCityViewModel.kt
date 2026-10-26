package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.Category
import com.example.mycity.data.LocalPlacesDataProvider
import com.example.mycity.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState.asStateFlow()

    /**
     * Выбирает категорию и сразу выделяет в ней первое место,
     * чтобы на больших экранах панель с деталями не оставалась пустой.
     */
    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category,
                currentPlace = LocalPlacesDataProvider.getPlacesByCategory(category).first()
            )
        }
    }

    fun updateCurrentPlace(place: Place) {
        _uiState.update { it.copy(currentPlace = place) }
    }
}
