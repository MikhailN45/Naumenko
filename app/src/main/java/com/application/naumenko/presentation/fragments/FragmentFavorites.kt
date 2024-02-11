package com.application.naumenko.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.application.naumenko.R
import com.application.naumenko.databinding.FragmentFavoritesBinding
import com.application.naumenko.presentation.adapters.AdapterFavorites
import com.application.naumenko.presentation.adapters.ItemClickListener
import com.application.naumenko.presentation.states.FavoritesUiState
import com.application.naumenko.presentation.viewmodels.ViewModelFavorites
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentFavorites : Fragment() {
    private val viewModel: ViewModelFavorites by viewModels()
    private lateinit var binding: FragmentFavoritesBinding

    private val adapter = AdapterFavorites()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoritesBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoritesRecycler.adapter = adapter
        observeState()
        setListeners()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.favoritesRecycler.visibility =
                if (state is FavoritesUiState.Success) View.VISIBLE else View.INVISIBLE

            val dataset = if (state is FavoritesUiState.Success) state.films
            else emptyList()

            adapter.submitList(dataset)
        }
    }

    private fun setListeners() {
        adapter.setItemListener(object : ItemClickListener {
            override fun onItemClick(id: Int) {
                view?.findNavController()?.navigate(
                    R.id.action_favoritesFragment_to_filmDetailsFragment,
                    FragmentFilmDetails.createArguments(id = id)
                )
            }

            override fun onItemRemoveClick(id: Int) {
                viewModel.onItemRemoveClicked(id)
            }
        })
        binding.popularButtonFilmList.setOnClickListener {
            it.findNavController().navigate(R.id.action_favoritesFragment_to_filmListsFragment)
        }
    }
}