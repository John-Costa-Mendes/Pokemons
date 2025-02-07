package com.example.pokemons.data.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<Type>,
    val height: Int,
    val weight: Int
) {
    data class Sprites(val frontDefault: String)
    data class Type(val type: TypeInfo)
    data class TypeInfo(val name: String)
}