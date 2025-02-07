package com.example.pokemons.data.repository

import com.example.pokemons.data.model.PokemonDetail
import com.example.pokemons.data.model.PokemonResponse
import com.example.pokemons.data.remote.RetrofitInstance

class PokemonRepository {
    private val api = RetrofitInstance.pokemonApiService

    suspend fun getPokemons(limit: Int, offset: Int): PokemonResponse {
        return api.getPokemons(limit, offset)
    }

    suspend fun getPokemonDetails(id: Int): PokemonDetail {
        return api.getPokemonDetails(id)
    }
}