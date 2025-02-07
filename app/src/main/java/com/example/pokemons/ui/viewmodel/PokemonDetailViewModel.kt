package com.example.pokemons.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemons.data.model.PokemonDetail
import com.example.pokemons.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val id: Int
) : ViewModel() {
    private val _pokemon = MutableLiveData<PokemonDetail>()
    val pokemon: LiveData<PokemonDetail> get() = _pokemon

    init {
        viewModelScope.launch {
            try {
                val result = repository.getPokemonDetails(id)
                _pokemon.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}