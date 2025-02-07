package com.example.pokemons.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemons.data.repository.PokemonRepository
import com.example.pokemons.databinding.ActivityMainBinding
import com.example.pokemons.ui.adapter.PokemonAdapter
import com.example.pokemons.ui.viewmodel.PokemonMainViewModel
import com.example.pokemons.ui.viewmodel.PokemonMainViewModelFactory
import kotlinx.coroutines.launch

class PokemonMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PokemonMainViewModel
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = PokemonRepository()
        viewModel = ViewModelProvider(
            this,
            PokemonMainViewModelFactory(repository)
        )[PokemonMainViewModel::class.java]

        adapter = PokemonAdapter { pokemonId ->
            val intent = Intent(
                this, PokemonDetailActivity::class.java
            ).apply {
                putExtra("pokemon_id", pokemonId)
            }
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch {
            viewModel.pokemons.collect { pagingData ->
                adapter.submitData(pagingData) // Correto: PagingData<Pokemon>
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadStates ->
                binding.progressBar.visibility =
                    if (loadStates.refresh is LoadState.Loading) View.VISIBLE else View.GONE
            }
        }
    }
}