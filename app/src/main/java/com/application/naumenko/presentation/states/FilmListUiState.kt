package com.application.naumenko.presentation.states

import com.application.naumenko.domain.model.Film

sealed interface FilmListUiState {
    data object Loading : FilmListUiState
    data class Success(val films: List<Film>) : FilmListUiState
    data object Error : FilmListUiState
}