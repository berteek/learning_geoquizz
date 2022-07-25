package com.berteek.geoquizz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.berteek.geoquizz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: GeoQuizzViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateQuestion()
        setupListeners()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "Activity destroyed")
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
        binding.trueButton.setOnClickListener {
            answerButtonSubroutine(true)
        }

        binding.falseButton.setOnClickListener {
            answerButtonSubroutine(false)
        }

        binding.nextButton.setOnClickListener {
            viewModel.nextQuestion()
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            viewModel.previousQuestion()
            updateQuestion()
        }

        binding.questionText.setOnClickListener {
            viewModel.nextQuestion()
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
        val message =
            if (viewModel.currentQuestionCorrectAnswer == viewModel.currentQuestionUserAnswer)
                R.string.correct_message
            else
                R.string.incorrect_message

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