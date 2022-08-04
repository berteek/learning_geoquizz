package com.berteek.geoquizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.berteek.geoquizz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: GeoQuizzViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isCurrentQuestionCheated = result.data?.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false) ?: false
        if (result.resultCode == RESULT_OK && isCurrentQuestionCheated) {
            viewModel.markCurrentQuestionAsCheated()
            updateCheats()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateQuestion()
        setupListeners()
        updateCheats()
    }

    private fun updateCheats() {
        updateCheatsLeftText()
        disableCheatButtonIfNoCheatsLeft()
    }

    private fun updateCheatsLeftText() {
        binding.cheatsLeftText.text = getString(R.string.cheats_left, viewModel.cheatsLeft)
    }

    private fun disableCheatButtonIfNoCheatsLeft() {
        if (viewModel.cheatsLeft <= 0)
            binding.cheatButton.isEnabled = false
    }


    private fun setButtonsIsEnabled(isEnabled: Boolean) {
        binding.trueButton.isEnabled = isEnabled
        binding.falseButton.isEnabled = isEnabled
    }

    private fun updateQuestion() {
        binding.questionText.setText(viewModel.currentQuestionText)

        if (viewModel.currentQuestionUserAnswer == null)
            setButtonsIsEnabled(true)
        else
            setButtonsIsEnabled(false)
    }

    private fun setupListeners() {
        binding.questionText.setOnClickListener {
            viewModel.nextQuestion()
            updateQuestion()
        }

        binding.trueButton.setOnClickListener {
            answerButtonSubroutine(true)
        }
        binding.falseButton.setOnClickListener {
            answerButtonSubroutine(false)
        }

        binding.cheatButton.setOnClickListener {
            val intent = CheatActivity.newIntent(this@MainActivity, viewModel.currentQuestionCorrectAnswer)
            cheatLauncher.launch(intent)
        }

        binding.nextButton.setOnClickListener {
            viewModel.nextQuestion()
            updateQuestion()
        }
        binding.previousButton.setOnClickListener {
            viewModel.previousQuestion()
            updateQuestion()
        }
    }

    private fun answerButtonSubroutine(userAnswer: Boolean) {
        viewModel.registerUserAnswer(userAnswer)
        checkAnswer()
        updateQuestion()
        checkIfAllAnswered()
    }

    private fun checkAnswer() {
        val message = when {
            viewModel.hasCheatedOnCurrentQuestion -> R.string.judgement_snackbar
            viewModel.currentQuestionCorrectAnswer == viewModel.currentQuestionUserAnswer -> R.string.correct_message
            else -> R.string.incorrect_message
        }

        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun checkIfAllAnswered() {
        val areAllAnswered = viewModel.checkIfAllAnswered()
        if (areAllAnswered)
            Snackbar.make(
                binding.root,
                getString(
                    R.string.score_message,
                    viewModel.calculateCorrectAnswered(),
                    viewModel.questions.size
                ),
                Snackbar.LENGTH_SHORT
            ).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}