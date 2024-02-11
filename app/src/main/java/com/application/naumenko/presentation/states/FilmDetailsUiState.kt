package com.application.naumenko.presentation.states

import com.application.naumenko.domain.model.About

sealed interface FilmDetailsUiState {
    data object Loading : FilmDetailsUiState
    data class Success(val about: About, val isDb: Boolean) : FilmDetailsUiState
    data object Error : FilmDetailsUiState
}