package com.example.parcialluisbetancourt.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.parcialluisbetancourt.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AppViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        sharedPreferences = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        
        // Cargar color guardado
        val savedColor = sharedPreferences.getInt("backgroundColor", 0xFFFFFFFF.toInt())
        viewModel.updateBackgroundColor(savedColor)

        setupColorButtons()
        setupBackButton()
    }

    private fun setupColorButtons() {
        binding.apply {
            buttonWhite.setOnClickListener { setBackgroundColor(0xFFFFFFFF.toInt()) }
            buttonBlue.setOnClickListener { setBackgroundColor(0xFF2196F3.toInt()) }
            buttonGreen.setOnClickListener { setBackgroundColor(0xFF4CAF50.toInt()) }
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setBackgroundColor(color: Int) {
        viewModel.updateBackgroundColor(color)
        sharedPreferences.edit().putInt("backgroundColor", color).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 