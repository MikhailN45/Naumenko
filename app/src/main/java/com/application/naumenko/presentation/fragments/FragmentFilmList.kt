package com.application.naumenko.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.application.naumenko.R
import com.application.naumenko.databinding.FragmentFilmListBinding
import com.application.naumenko.presentation.adapters.AdapterFilmList
import com.application.naumenko.presentation.adapters.ItemClickListener
import com.application.naumenko.presentation.states.FilmListUiState
import com.application.naumenko.presentation.viewmodels.ViewModelFilmList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentFilmList : Fragment() {

    private val viewModel: ViewModelFilmList by viewModels()
    private lateinit var binding: FragmentFilmListBinding
    private val adapter = AdapterFilmList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFilmListBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.popularRecycler.adapter = adapter
        observeState()
        setListeners()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.popularRecycler.visibility =
                if (state is FilmListUiState.Success) View.VISIBLE else View.INVISIBLE
            val dataset = if (state is FilmListUiState.Success) state.films
            else emptyList()
            adapter.submitList(dataset)

            with(binding.errorViewLayoutFilmList) {
                networkErrorView.visibility =
                    if (state is FilmListUiState.Success) View.GONE else View.VISIBLE

                val setStatus =
                    if (state is FilmListUiState.Loading) R.drawable.progressbar_animated else R.drawable.no_connect_error_icon
                noConnectionIcon.setImageResource(setStatus)

                noConnectionErrorMessage.visibility = View.VISIBLE
                    if (state is Error) View.VISIBLE else View.GONE


                retryConnectionButton.visibility = View.VISIBLE
                    if (state is Error) View.VISIBLE else View.GONE

            }
        }
    }

    private fun setListeners() {
        adapter.setItemListener(object : ItemClickListener {
            override fun onItemClick(id: Int) {
                viewModel.onItemClick(id)
            }

            override fun onItemRemoveClick(id: Int) {
                viewModel.onItemRemoveClick(id)
            }
        })
        binding.favoritesButtonFilmList.setOnClickListener {
            it.findNavController()
                .navigate(R.id.filmListFragment_to_favoritesFragment)
        }
        binding.errorViewLayoutFilmList.retryConnectionButton
            .setOnClickListener { viewModel.onRetryButtonPressed() }
    }
}
