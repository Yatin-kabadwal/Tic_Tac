package com.example.tictactoeapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private var playerTurn = true // true = Player X, false = Player O
    private var boardStatus = Array(3) { IntArray(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            findViewById(R.id.button0), findViewById(R.id.button1), findViewById(R.id.button2),
            findViewById(R.id.button3), findViewById(R.id.button4), findViewById(R.id.button5),
            findViewById(R.id.button6), findViewById(R.id.button7), findViewById(R.id.button8)
        )

        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                onButtonClick(i)
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetGame()
        }

        resetGame()
    }

    private fun onButtonClick(index: Int) {
        val row = index / 3
        val col = index % 3

        if (boardStatus[row][col] == 0) {
            if (playerTurn) {
                buttons[index].text = "X"
                boardStatus[row][col] = 1
            } else {
                buttons[index].text = "O"
                boardStatus[row][col] = 2
            }

            buttons[index].isEnabled = false

            if (checkWin()) {
                Toast.makeText(this, "Player ${if (playerTurn) "X" else "O"} wins!", Toast.LENGTH_LONG).show()
                disableAllButtons()
            } else {
                playerTurn = !playerTurn
            }
        }
    }

    private fun checkWin(): Boolean {
        // Check rows
        for (i in 0..2) {
            if (boardStatus[i][0] != 0 && boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2]) {
                return true
            }
        }

        // Check columns
        for (i in 0..2) {
            if (boardStatus[0][i] != 0 && boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i]) {
                return true
            }
        }

        // Check diagonals
        if (boardStatus[0][0] != 0 && boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2]) {
            return true
        }

        if (boardStatus[0][2] != 0 && boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0]) {
            return true
        }

        return false
    }

    private fun resetGame() {
        playerTurn = true
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = 0
            }
        }

        for (button in buttons) {
            button.text = ""
            button.isEnabled = true
        }
    }

    private fun disableAllButtons() {
        for (button in buttons) {
            button.isEnabled = false
        }
    }
}
