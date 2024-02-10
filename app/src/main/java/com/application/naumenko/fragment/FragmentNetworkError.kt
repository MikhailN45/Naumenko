package com.application.naumenko.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.naumenko.databinding.FragmentErrorBinding

class FragmentNetworkError: Fragment() {
    private lateinit var binding: FragmentErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retryConnection = binding.retryConnectionButton
        retryConnection.setOnClickListener {}

        //handle retry connection button click
    }
}