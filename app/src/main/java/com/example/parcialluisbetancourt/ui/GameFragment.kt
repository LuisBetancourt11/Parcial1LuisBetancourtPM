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
import androidx.navigation.fragment.findNavController
import androidx.compose.runtime.livedata.observeAsState
import java.text.SimpleDateFormat
import java.util.*

class GameFragment : Fragment() {
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
                    GameScreen(viewModel, onBackClick = { findNavController().navigateUp() })
                }
            }
        }
    }
}

@Composable
fun GameScreen(viewModel: AppViewModel, onBackClick: () -> Unit) {
    val gameBoard by viewModel.gameBoard.observeAsState(initial = Array(3) { Array(3) { "" } })
    val currentPlayer by viewModel.currentPlayer.observeAsState(initial = "X")
    val gameStatus by viewModel.gameStatus.observeAsState(initial = "Turno de X")
    val isGameOver by viewModel.isGameOver.observeAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = "Triqui",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            text = gameStatus,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        gameBoard.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEachIndexed { colIndex, cell ->
                    Button(
                        onClick = {
                            if (!isGameOver && cell.isEmpty()) {
                                viewModel.makeMove(rowIndex, colIndex)
                            }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .size(100.dp),
                        enabled = !isGameOver && cell.isEmpty()
                    ) {
                        Text(
                            text = cell,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
            }
        }

        if (isGameOver) {
            Button(
                onClick = { viewModel.resetGame() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Jugar de nuevo")
            }
        }
    }
} 