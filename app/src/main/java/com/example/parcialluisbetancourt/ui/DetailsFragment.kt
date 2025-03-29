package com.example.parcialluisbetancourt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    DetailsScreen(viewModel, onBackClick = { findNavController().navigateUp() })
                }
            }
        }
    }
}

@Composable
fun DetailsScreen(viewModel: AppViewModel, onBackClick: () -> Unit) {
    val scoreX by viewModel.scoreX.observeAsState(initial = 0)
    val scoreO by viewModel.scoreO.observeAsState(initial = 0)
    val lastUpdateTime by viewModel.lastUpdateTime.observeAsState(initial = "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Volver")
            }
            Text(
                text = "Marcador",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Jugador X: $scoreX",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Jugador O: $scoreO",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "Última actualización: $lastUpdateTime",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
} 