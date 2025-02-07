package com.example.pokemons.ui.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemons.data.model.Pokemon
import com.example.pokemons.data.repository.PokemonRepository

class PokemonPagingSource(
    private val repository: PokemonRepository,
    private val limit: Int
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val page = params.key ?: 0
            val loadSize = params.loadSize.coerceAtMost(limit - page)

            if (loadSize <= 0) {
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
            }

            val response = repository.getPokemons(limit = loadSize, offset = page)

            val nextKey = if (page + response.results.size >= limit) null else page + loadSize

            LoadResult.Page(data = response.results, prevKey = null, nextKey = nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return null
    }
}