package com.application.naumenko.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.naumenko.domain.interactors.FilmInteractor
import com.application.naumenko.presentation.states.FilmDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFilmDetails @Inject constructor(private val filmInteractor: FilmInteractor) :
    ViewModel() {
    private val _state = MutableLiveData<FilmDetailsUiState>()
    val state: LiveData<FilmDetailsUiState> = _state

    var filmId: Int = 0
        set(value) {
            field = value
            loadState()
        }

    fun onRetryButtonPressed() {
        loadState()
    }

    private fun loadState() {
        viewModelScope.launch {
            _state.value = FilmDetailsUiState.Loading
            try {
                val about = filmInteractor.selectId(filmId)
                _state.value = if (about == null)
                    FilmDetailsUiState.Success(filmInteractor.getFilmDetails(filmId), false)
                else
                    FilmDetailsUiState.Success(about, true)
            } catch (exception: Exception) {
                _state.value = FilmDetailsUiState.Error
            }
        }
    }

}