package com.application.naumenko.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.naumenko.domain.interactors.FilmInteractor
import com.application.naumenko.presentation.states.FilmListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFilmList @Inject constructor(private val filmInteractor: FilmInteractor) :
    ViewModel() {

    private val _state = MutableLiveData<FilmListUiState>()
    val state: LiveData<FilmListUiState> = _state

    init {
        loadState()
    }

    fun onRetryButtonPressed() {
        loadState()
    }

    fun onItemRemoveClick(id: Int) {
        viewModelScope.launch {
            try {
                filmInteractor.removeFavorite(id)
            } catch (exception: Exception) {
                _state.value = FilmListUiState.Error
            }
        }
    }

    fun onItemClick(id: Int) {
        viewModelScope.launch {
            try {
                val filmInfo = filmInteractor.getFilmDetails(id)
                filmInfo.isFavorite = true
                filmInteractor.addFavorite(filmInfo)
            } catch (exception: Exception) {
                _state.value = FilmListUiState.Error
            }
        }
    }

    private fun loadState() {
        viewModelScope.launch {
            _state.value = FilmListUiState.Loading
            try {
                val films = filmInteractor.getTopFilms().films
                val favorites = filmInteractor.getFavorites()
                val compareIds =
                    films.map { it.filmId }.intersect(favorites.map { it.kinopoiskId }.toSet())
                films.forEach { film ->
                    if (compareIds.contains(film.filmId)) film.isFavorite = true
                }
                _state.value = FilmListUiState.Success(films)
            } catch (exception: Exception) {
                _state.value = FilmListUiState.Error
            }
        }
    }
}