package com.example.parcialluisbetancourt.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class AppViewModel : ViewModel() {
    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int> = _backgroundColor

    private val _gameBoard = MutableLiveData<Array<Array<String>>>()
    val gameBoard: LiveData<Array<Array<String>>> = _gameBoard

    private val _currentPlayer = MutableLiveData<String>()
    val currentPlayer: LiveData<String> = _currentPlayer

    private val _gameStatus = MutableLiveData<String>()
    val gameStatus: LiveData<String> = _gameStatus

    private val _scoreX = MutableLiveData<Int>()
    val scoreX: LiveData<Int> = _scoreX

    private val _scoreO = MutableLiveData<Int>()
    val scoreO: LiveData<Int> = _scoreO

    private val _lastUpdateTime = MutableLiveData<String>()
    val lastUpdateTime: LiveData<String> = _lastUpdateTime

    private val _isGameOver = MutableLiveData<Boolean>()
    val isGameOver: LiveData<Boolean> = _isGameOver

    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    init {
        _backgroundColor.value = 0xFFFFFFFF.toInt() // Color blanco por defecto
        _gameBoard.value = Array(3) { Array(3) { "" } }
        _currentPlayer.value = "X"
        _gameStatus.value = "Turno de X"
        _scoreX.value = 0
        _scoreO.value = 0
        _lastUpdateTime.value = "Nunca"
        _isGameOver.value = false
    }

    fun updateBackgroundColor(color: Int) {
        _backgroundColor.value = color
    }

    fun makeMove(row: Int, col: Int) {
        val board = _gameBoard.value?.clone() ?: return
        if (board[row][col].isEmpty()) {
            board[row][col] = _currentPlayer.value ?: "X"
            _gameBoard.value = board

            if (checkWinner(board)) {
                _gameStatus.value = "¡${_currentPlayer.value} ha ganado!"
                _isGameOver.value = true
                updateScore()
            } else if (isBoardFull(board)) {
                _gameStatus.value = "¡Empate!"
                _isGameOver.value = true
            } else {
                _currentPlayer.value = if (_currentPlayer.value == "X") "O" else "X"
                _gameStatus.value = "Turno de ${_currentPlayer.value}"
            }
        }
    }

    fun resetGame() {
        _gameBoard.value = Array(3) { Array(3) { "" } }
        _currentPlayer.value = "X"
        _gameStatus.value = "Turno de X"
        _isGameOver.value = false
    }

    private fun checkWinner(board: Array<Array<String>>): Boolean {
        // Verificar filas
        for (i in 0..2) {
            if (board[i][0].isNotEmpty() &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2]) {
                return true
            }
        }

        // Verificar columnas
        for (j in 0..2) {
            if (board[0][j].isNotEmpty() &&
                board[0][j] == board[1][j] &&
                board[1][j] == board[2][j]) {
                return true
            }
        }

        // Verificar diagonales
        if (board[0][0].isNotEmpty() &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]) {
            return true
        }

        if (board[0][2].isNotEmpty() &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]) {
            return true
        }

        return false
    }

    private fun isBoardFull(board: Array<Array<String>>): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun updateScore() {
        if (_currentPlayer.value == "X") {
            _scoreX.value = (_scoreX.value ?: 0) + 1
        } else {
            _scoreO.value = (_scoreO.value ?: 0) + 1
        }
        _lastUpdateTime.value = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
    }
} 