package com.example.pokemons.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemons.R
import com.example.pokemons.data.model.Pokemon
import com.example.pokemons.data.model.PokemonDetail
import com.example.pokemons.databinding.ItemPokemonBinding
import java.util.Locale

class PokemonAdapter(private val onItemClick: (Int) -> Unit) :
    PagingDataAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let { pokemon ->
            holder.bind(pokemon)
        }
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            val id = extractIdFromUrl(pokemon.url)

            // Carrega a imagem usando o ID
            Glide.with(binding.root)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(binding.pokemonImage)

            binding.pokemonName.text = pokemon.name.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault()) else it.toString()
            }
            binding.pokemonId.text = String.format("#%03d", id)

            binding.root.setOnClickListener { onItemClick(id) } // Passa o ID do Pokémon
        }
    }

    private fun extractIdFromUrl(url: String): Int {
        return url.split("/").dropLast(1).last().toIntOrNull() ?: 0
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }
}