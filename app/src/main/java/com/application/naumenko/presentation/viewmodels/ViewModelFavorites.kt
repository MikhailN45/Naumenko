package com.application.naumenko.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.naumenko.domain.interactors.FilmInteractor
import com.application.naumenko.presentation.states.FavoritesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFavorites @Inject constructor(private val filmInteractor: FilmInteractor) :
    ViewModel() {

    private val _state = MutableLiveData<FavoritesUiState>()
    val state: LiveData<FavoritesUiState> = _state

    init {
        loadState()
    }

    fun onItemRemoveClicked(id: Int) {
        viewModelScope.launch {
            try {
                filmInteractor.removeFavorite(id)
            } catch (exception: Exception) {
                _state.value = FavoritesUiState.Error
            }
        }
    }

    private fun loadState() {
        viewModelScope.launch {
            _state.value = FavoritesUiState.Loading
            try {
                val films = filmInteractor.getFavorites()
                _state.value = FavoritesUiState.Success(films)
            } catch (exception: Exception) {
                _state.value = FavoritesUiState.Error
            }
        }
    }
}