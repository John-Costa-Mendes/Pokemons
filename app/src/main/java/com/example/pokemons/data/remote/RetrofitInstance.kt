package com.example.pokemons.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val retrofitInstance by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pokemonApiService: PokemonApiService by lazy {
        retrofitInstance.create(PokemonApiService::class.java)
    }
}
