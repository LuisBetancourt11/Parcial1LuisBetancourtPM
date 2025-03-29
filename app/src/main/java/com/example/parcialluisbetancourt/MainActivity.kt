package com.example.parcialluisbetancourt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.parcialluisbetancourt.databinding.ActivityMainBinding
import com.example.parcialluisbetancourt.ui.AppViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            viewModel = ViewModelProvider(this)[AppViewModel::class.java]

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            // Observar cambios en el color de fondo
            viewModel.backgroundColor.observe(this) { color ->
                binding.root.setBackgroundColor(color)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
} 