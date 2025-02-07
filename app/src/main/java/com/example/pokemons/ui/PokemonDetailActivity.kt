package com.example.pokemons.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokemons.R
import com.example.pokemons.data.repository.PokemonRepository
import com.example.pokemons.databinding.ActivityPokemonDetailBinding
import com.example.pokemons.ui.viewmodel.PokemonDetailViewModel
import com.example.pokemons.ui.viewmodel.PokemonDetailViewModelFactory
import java.util.Locale

class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var viewModel: PokemonDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonId = intent.getIntExtra("pokemon_id", 0)

        val repository = PokemonRepository()
        viewModel = ViewModelProvider(
            this,
            PokemonDetailViewModelFactory(repository, pokemonId)
        )[PokemonDetailViewModel::class.java]

        viewModel.pokemon.observe(this) { pokemon ->
            if (pokemon != null) {
                val imageUrl =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(binding.imageView)

                binding.nameTextView.text = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                binding.idTextView.text = String.format("#%03d", pokemon.id)
                binding.typesTextView.text =
                    "Types: ${pokemon.types.joinToString(", ") { it.type.name }}"
                binding.heightTextView.text = "Height: ${pokemon.height / 10.0} m"
                binding.weightTextView.text = "Weight: ${pokemon.weight / 10.0} kg"

                binding.progressBar.visibility = View.GONE
                binding.dataContainer.visibility = View.VISIBLE
                binding.imageView.visibility = View.VISIBLE
                binding.nameTextView.visibility = View.VISIBLE
                binding.typesTextView.visibility = View.VISIBLE
                binding.heightTextView.visibility = View.VISIBLE
                binding.weightTextView.visibility = View.VISIBLE
            }
        }
    }
}