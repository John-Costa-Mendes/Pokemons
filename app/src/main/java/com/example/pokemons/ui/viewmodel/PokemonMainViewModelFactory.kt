package com.example.pokemons.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemons.data.repository.PokemonRepository

class PokemonMainViewModelFactory(private val repository: PokemonRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonMainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonMainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}