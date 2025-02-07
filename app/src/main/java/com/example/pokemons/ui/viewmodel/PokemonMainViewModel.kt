package com.example.pokemons.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemons.data.model.Pokemon
import com.example.pokemons.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonMainViewModel(private val repository: PokemonRepository) : ViewModel() {
    val pokemons: Flow<PagingData<Pokemon>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PokemonPagingSource(repository, limit = 150) }
    ).flow.cachedIn(viewModelScope)
}