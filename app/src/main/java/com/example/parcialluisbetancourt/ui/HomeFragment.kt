package com.example.parcialluisbetancourt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.parcialluisbetancourt.R
import com.example.parcialluisbetancourt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        setupButtons()
        observeBackgroundColor()
    }

    private fun observeBackgroundColor() {
        viewModel.backgroundColor.observe(viewLifecycleOwner) { color ->
            binding.root.setBackgroundColor(color)
        }
    }

    private fun setupButtons() {
        binding.detailsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_details)
        }

        binding.gameButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_game)
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 