package com.application.naumenko.presentation.states

import com.application.naumenko.domain.model.About

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val films: List<About>) : FavoritesUiState
    data object Error : FavoritesUiState
}